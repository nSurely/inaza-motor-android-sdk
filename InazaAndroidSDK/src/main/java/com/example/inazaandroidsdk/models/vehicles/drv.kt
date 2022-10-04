package com.example.inazaandroidsdk.models.vehicles

import api.ApiHandler
import com.example.inazaandroidsdk.helpers.checkApiHandler
import helpers.KOffsetDateTimeSerializer
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.custom.PrivateApiHandler
import models.drivers.Driver
import kotlinx.serialization.json.decodeFromJsonElement
import models.policy.Policy
import models.policy.enums.PolicyGroup
import models.risk.CommonRisk
import java.time.OffsetDateTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Serializable
data class DriverVehicle(
    /**
     * Driver vehicle
     *
     * @property api ApiHandler object used to communicate with Inaza's API
     * @property externalId ??
     * @property displayName Text field uploaded by the driver to quickly identify the DRV during selection.
     * @property isApproved If this vehicle has been approved to be insured internally.
     * @property approvedAt The ISO timestamp of when this vehicle was approved internally.
     * @property isOwner Is the attached driver is the owner of the registered vehicle.
    Any base premium billing events will be charged to the driver who is the owner.
     * @property isDefault If this is the drivers default DRV, it will be their default vehicle for display and tracking purposes.
     * @property isActive If this DRV is active. If not, then it will not be displayed and tracking cannot take place.
     * @property isPrimaryDriver If this driver is the primary driver on this vehicle.
     * @property expiresAt ISO timestamp of when this DRV expires.
    Once expired, the isActive field is set to False.
    This is useful for creating temporary access to a registered vehicle, eg. rental insurance.
    If left as null, this DRV never expires.
     * @property risk
     * @property id The unique ID of this registered vehicle.
     * @property sourceId The source ID (telematics tracking ID).
    This source ID type is often used in fleet management.
    Any billing events tied to this ID will be charged back to a driver through a DRV, or to the attached fleet.

    > Contact nSurely to see what source ID is best for your use-case.
     * @property createdAt When this RV was added to the system.
     * @property proofOfOwnershipLoc
     * @property driver
     * @property registeredVehicle The vehicle this DRV is associated with.
     * @property driverId ID of the associated driver.
     * @constructor Create empty Driver vehicle
     */
    @Transient
    override var api: ApiHandler? = null,

    @SerialName("externalId")
    private val _externalId: String? = null,

    @SerialName("displayName")
    private val _displayName: String? = null,

    @SerialName("isApproved")
    private val _isApproved: Boolean = false,

    @SerialName("approvedAt")
    @Serializable(with = KOffsetDateTimeSerializer::class)
    private val _approvedAt: OffsetDateTime? = null,

    @SerialName("isOwner")
    private val _isOwner: Boolean = false,

    @SerialName("isDefault")
    private val _isDefault: Boolean = false,

    @SerialName("isActive")
    private val _isActive: Boolean = false,

    @SerialName("isPrimaryDriver")
    private val _isPrimaryDriver: Boolean = false,

    @SerialName("expiresAt")
    @Serializable(with = KOffsetDateTimeSerializer::class)
    private val _expiresAt: OffsetDateTime? = null,

    @SerialName("risk")
    private val _risk: CommonRisk? = null,

    @SerialName("id")
    private val _id: String? = null,

    @SerialName("sourceId")
    private val _sourceId: String? = null,

    @SerialName("createdAt")
    private val _createdAt: String? = null,

    @SerialName("proofOfOwnershipLoc")
    private val _proofOfOwnershipLoc: String? = null,

    @SerialName("driver")
    private val _driver: Driver? = null,

    @SerialName("registeredVehicle")
    private val _registeredVehicle: Vehicle? = null,

    // Not returned from API set manually
    private val _driverId: String? = null
) : PrivateApiHandler() {
    @kotlinx.serialization.Transient
    var changedFields = mutableMapOf<String, Any?>()

    var externalId: String? by Delegates.observable(_externalId, ::watcher)
    var approvedAt: OffsetDateTime? by Delegates.observable(_approvedAt, ::watcher)
    var displayName: String? by Delegates.observable(_displayName, ::watcher)
    var isApproved: Boolean by Delegates.observable(_isApproved, ::watcher)
    var isOwner: Boolean by Delegates.observable(_isOwner, ::watcher)
    var isDefault: Boolean by Delegates.observable(_isDefault, ::watcher)
    var isActive: Boolean by Delegates.observable(_isActive, ::watcher)
    var isPrimaryDriver: Boolean by Delegates.observable(_isPrimaryDriver, ::watcher)
    var expiresAt: OffsetDateTime? by Delegates.observable(_expiresAt, ::watcher)
    var risk: CommonRisk? by Delegates.observable(_risk, ::watcher)
    val id: String? by Delegates.observable(_id, ::watcher)
    val sourceId: String? by Delegates.observable(_sourceId, ::watcher)
    val createdAt: String? by Delegates.observable(_createdAt, ::watcher)
    var proofOfOwnershipLoc: String? by Delegates.observable(_proofOfOwnershipLoc, ::watcher)

    var driver: Driver? by Delegates.observable(_driver, ::watcher)
    var registeredVehicle: Vehicle? by Delegates.observable(_registeredVehicle, ::watcher)
    var driverId: String? by Delegates.observable(_driverId, ::watcher)
    private fun watcher(prop: KProperty<*>, old: Any?, new: Any?) {
        if (old != new) {
            println("${prop.name} has changed from $old to $new")
            changedFields[prop.name] = new
        }
    }

    private suspend fun checkId() {
        if (this.id == null || this.driverId == null) {
            throw Exception("Id and driverId must be set")
        }
    }

    /**
     * A simple display string to identify the model to the user.
     *
     * @return
     */
    suspend fun getDisplay(): String {
        /*A simple display string to identify the model to the user.*/

        return "${this.registeredVehicle?.getDisplay()} - this.regPlate}"
    }

    /**
     * Return the telematics ID.
     *
     * @return
     */
    suspend fun telematicsId(): String? {
        return this.sourceId
    }

    /**
     * List policies for this vehicle.
     *
     * @param looseMatch If True, will match on the DRV ID and the vehicle ID.
     * @param isActivePolicy If True, will return only active policies. Defaults to None.
     * @return
     */
    suspend fun listPolicies(
        looseMatch: Boolean = true,
        isActivePolicy: Boolean? = null
    ): Flow<Policy> = flow {
        val params: MutableMap<String, String?> =
            mutableMapOf<String, String?>("limit" to "50", "drvIds" to this@DriverVehicle.id)
        if (looseMatch) {
            params["rvIds"] = this@DriverVehicle.registeredVehicle?.id
        }

        if (isActivePolicy != null) {
            params["isActivePolicy"] = isActivePolicy.toString()
        }
        this@DriverVehicle.api?.batchFetch("policy", params, null)
            ?.collect { res ->
                val policy: Policy = Json.decodeFromJsonElement(res)
                policy.api = this@DriverVehicle.api
                emit(policy)
            }
    }

    /**
     * Create policy
     *
     * @param policy Policy to create. This can be left None and a new policy will be created using the org defaults.
     * @return
     */
    suspend fun createPolicy(policy: Policy? = null): Policy? {
        checkApiHandler(this.api)
        this.checkId()
        if (this.api == null) {
            throw Exception("API handler is required")
        }
        val p = policy ?: Policy(this.api)

        p.policyGroup = PolicyGroup.drv

        val pol = this.id?.let { p.create(apiHandler = this.api!!, recordId = it) }

        return pol
    }

    /**
     * Create a DRV
     *
     * @param driverId Driver ID.
     * @return A DriverVehicle model.
     */
    suspend fun create(driverId: String): DriverVehicle {
        checkApiHandler(this.api)
        return Json.decodeFromString(this.api!!.makeAuthRequest("POST","driver/${driverId}/vehicles", data = this).readText())
    }

    /**
     * Refresh the model from the API.
     *
     */
    suspend fun refresh() {
        this.checkId()


        val refresh: DriverVehicle =
            Json.decodeFromString(
                this.api!!.makeAuthRequest(
                    "GET",
                    "/drivers/${this.driverId}/vehicles/${this.id}"
                ).readText()
            )

        for (p in DriverVehicle::class.memberProperties) {
            val prop = p.name

            val value = DriverVehicle::class.memberProperties
                .first { it.name == prop }
                .also { it.isAccessible = true } // Skip private properties
                .getter(refresh)

            this.setPropertyValue(prop, value)
        }
    }

    /**
     * Delete the driver vehicle via the API.
     *
     */
    suspend fun delete() {
        this.checkId()
        this.api!!.makeAuthRequest("DELETE", "/drivers/${this.driverId}/vehicles/${this.id}")

    }

    /**
     * Persist any changes in the API.
     *
     * @param fields The API formatted fields to update. If not supplied, any set fields in the model will be updated in the API. Defaults to None.
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        val f = fields ?: this.changedFields
        this.modelSave("/drivers/${this.driverId}/vehicles/${this.id}", f)
    }

    /**
     * Update a field on the model, call save or keyword persist to persist changes in the API.
     *
     * @param persist Whether to persist the changes to the API. Defaults to False.
     * @param fields The model fields to update.
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        this.modelUpdate("/drivers/${this.driverId}/vehicles/${this.id}", persist, fields)
    }
}




