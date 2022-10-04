package models.fleets

import api.ApiHandler
import com.example.inazaandroidsdk.helpers.checkApiHandler
import helpers.KOffsetDateTimeSerializer
import helpers.enumContains
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import models.constants.Lang
import models.custom.PrivateApiHandler
import models.policy.Policy
import models.risk.Risk
import java.time.OffsetDateTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Fleet model.
 *
 * @property api
 * @property id
 * @property externalId
 * @property display
 * @property description
 * @property tags
 * @property isActive
 * @property requiresDriverAssignment
 * @property basePremiumBillingProc
 * @property ratesBillingProc
 * @property parentId
 * @property createdAt
 * @property translations
 * @property risk
 * @property driverCount
 * @property subFleetCount
 * @property vehicleCount
 * @constructor Create empty Fleet
 */

@kotlinx.serialization.Serializable
class Fleet(
    @kotlinx.serialization.Transient
    override var api: ApiHandler? = null,
    @SerialName("id") var _id: String? = null,

    @SerialName("externalId") var _externalId: String? = null,

    @SerialName("display") var _display: String,
    @SerialName("description") var _description: String? = null,
    @SerialName("tags") var _tags: String? = null,
    @SerialName("isActive") var _isActive: Boolean = true,
    @SerialName("requiresDriverAssignment") var _requiresDriverAssignment: Boolean = false,
    @SerialName("basePremiumBillingProc") var _basePremiumBillingProc: String = "self",
    @SerialName("ratesBillingProc") var _ratesBillingProc: String = "self",
    @SerialName("parentId") var _parentId: String? = null,
    // TODO default to current time if absent
    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("createdAt") var _createdAt: OffsetDateTime? = null,
    @SerialName("translations") var _translations: MutableMap<String, MutableMap<String, String>>? = null,

//    @SerialName("translations") var _translations: Translations? = null,
    @SerialName("risk") var _risk: Risk = Risk(),
    @SerialName("driverCount") var _driverCount: Int = 0,
    @SerialName("subFleetCount") var _subFleetCount: Int = 0,
    @SerialName("vehicleCount") var _vehicleCount: Int = 0
) : PrivateApiHandler() {
    @Transient
    var changedFields = mutableMapOf<String, Any?>()
    var id: String? by Delegates.observable(_id, ::watcher)
    var externalId: String? by Delegates.observable(_externalId, ::watcher)
    var display: String by Delegates.observable(_display, ::watcher)
    var description: String? by Delegates.observable(_description, ::watcher)
    var tags: String? by Delegates.observable(_tags, ::watcher)
    var isActive: Boolean by Delegates.observable(_isActive, ::watcher)
    var requiresDriverAssignment: Boolean by Delegates.observable(
        _requiresDriverAssignment,
        ::watcher
    )
    var basePremiumBillingProc: String by Delegates.observable(_basePremiumBillingProc, ::watcher)
    var ratesBillingProc: String by Delegates.observable(_ratesBillingProc, ::watcher)
    var parentId: String? by Delegates.observable(_parentId, ::watcher)
    var createdAt: OffsetDateTime? by Delegates.observable(_createdAt, ::watcher)

    //    var translations: Translations? by Delegates.observable(_translations, ::watcher)
    var translations: MutableMap<String, MutableMap<String, String>>? by Delegates.observable(
        _translations,
        ::watcher
    )
    var risk: Risk by Delegates.observable(_risk, ::watcher)
    var driverCount: Int by Delegates.observable(_driverCount, ::watcher)
    var subFleetCount: Int by Delegates.observable(_subFleetCount, ::watcher)
    var vehicleCount: Int by Delegates.observable(_vehicleCount, ::watcher)

    /**
     * Watcher
     *
     * @param prop
     * @param old
     * @param new
     */
    private fun watcher(prop: KProperty<*>, old: Any?, new: Any?) {
        if (old != new) {
            this.changedFields[prop.name] = new
        }
    }

    /**
     * Get the translation for the given key, optionally restricting to a specific language.
     *
     * @param key
     * @param lang
     * @return
     */
    private suspend fun getTranslations(key: String, lang: String? = null): Any? {
        if (lang == null) {
            val trans = this.translations?.get("key")

            return trans
        }

        if (!enumContains<Lang>(lang)) {
            throw Exception("$lang is not a supported language code")
        }

        val trans = this.translations?.get(key)?.get(lang)

        if (trans != null) {
            return trans
        }
        return null
    }

    /**
     * Get the display name for the fleet, optionally restricting to a specific language.
     *
     * @param lang
     * @return
     */
    suspend fun getDisplay(lang: String?): String? {
        @Suppress("UNCHECKED_CAST")
        return this.getTranslations("display", lang) as String?
    }

    /**
     * Get the description for the fleet, optionally restricting to a specific language.
     *
     * @param lang the ISO language code to retrieve. Defaults to null.
     */
    suspend fun getDescription(lang: String? = null): Any? {
        return this.getTranslations("description", lang)
    }

    /**
     * Check if the fleet has a parent fleet.
     *
     * @return
     */
    suspend fun hasParent(): Boolean {
        if (this.parentId != null) {
            return true
        }

        return false
    }

    /**
     * Get the tags for the fleet.
     *
     * @return
     */
    suspend fun getTags(): List<String> {
        if (this.tags != null) {
            return this.tags!!.split(",")
        }
        return listOf<String>()
    }

    private suspend fun checkId() {
        if (this.id == null) {
            throw Exception("Id must be set")
        }
    }

    /**
     * Refresh the model from the API.
     *
     */
    suspend fun refresh() {
        this.checkId()

        val refresh: Fleet = Json.decodeFromString(
            this.api!!.makeAuthRequest("GET", "/fleets/${this.id}").readText()
        )

        for (p in Fleet::class.memberProperties) {
            val prop = p.name

            val value = Fleet::class.memberProperties
                .first { it.name == prop }
                .also { it.isAccessible = true } // Skip private properties
                .getter(refresh)

            this.setPropertyValue(prop, value)
        }
    }

    /**
     * Delete this record via the API.
     *
     */
    suspend fun delete() {
        this.checkId()
        this.api?.makeAuthRequest("DELETE", "/fleets/${this.id}")
    }

    /**
     * Persist any changes in the API.
     *
     * @param fields
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        val f = fields ?: this.changedFields
        // TODO exclude vehicleType
        this.modelSave("/fleets/${this.id}", f)
    }

    /**
     * Update a field on the model, call save or keyword persist to persist changes in the API.
     *
     * @param persist Whether to persist the changes to the API. Defaults to false.
     * @param fields the model fields to update.
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        this.modelUpdate("/fleets/${this.id}", persist, fields)
    }

    /**
     * Get the parent fleet of the current fleet.
     *
     * @return
     */
    suspend fun getParent(): Fleet? {
        if (!this.hasParent()) {
            return null
        }
        val response: HttpResponse? =
            this.api?.makeAuthRequest("GET", "/fleets/${this.parentId}", null, null, null)

        return Json.decodeFromString<Fleet>(response!!.readText())
    }


    /**
     * Add a driver to the fleet.
     *
     * @param driverId The driver ID
     * @param isVehicleManager Can manage vehicles. Defaults to false.
     * @param isDriverManager Can manage drivers. Defaults to false.
     * @param isBillingManager Can manage billing details. Defaults to false.
     * @param expiresAt If and when the driver assignment expires. Defaults to null.
     * @param isActive If active in the fleet. Defaults to true.
     * @param vehicleIds The vehicle IDs to assign to the driver. Defaults to null.
     * @param vehiclesExpiresAt The vehicle assignment expiration dates. Defaults to null.
     * Note: Supply a single datetime for all vehicles, or a list of datetimes for each vehicle.
     * @return
     */
    suspend fun addDriver(
        driverId: String,
        isVehicleManager: Boolean = false,
        isDriverManager: Boolean = false,
        isBillingManager: Boolean = false,
        expiresAt: OffsetDateTime? = null,
        isActive: Boolean = true,
        vehicleIds: List<String>? = null,
        // TODO this is meant to be a union type
        vehiclesExpiresAt: List<OffsetDateTime>? = null
    ): FleetDriver? {

        checkApiHandler(this.api)

        /*Add a driver to the fleet*/
        var vIds: List<String> = listOf<String>()
        if (vehicleIds != null) {
            vIds = vehicleIds
        }

        val data = mutableMapOf<String, Any?>(
            "driverId" to driverId.toString(),
            "isVehicleManager" to isVehicleManager.toString(),
            "isDriverManager" to isDriverManager.toString(),
            "isBillingManager" to isBillingManager.toString(),
            // TODO iso format equiv?
            "expiresAt" to expiresAt,
            "isActive" to isActive.toString()
        )

        if (this.api != null) {
            val driverRes =
                this.api!!.makeAuthRequest("POST", "fleets/${this.id}/drivers", data = data)
            val driver = Json.decodeFromString<FleetDriver>(driverRes.readText())

            driver.api = this.api

            if (vIds.isEmpty()) {
                return driver
            }

            try {
                if (vehiclesExpiresAt != null) {
                    if (vehiclesExpiresAt.size == 1) {
                        for (vehicleId in vIds) {
                            this.api!!.makeAuthRequest(
                                "POST",
                                "/fleets/${this.id}/drivers/${driverId}/vehicles/${vehicleId}",
                                data = mapOf(
                                    "expiresAt" to vehiclesExpiresAt[0]
                                )
                            )
                        }
                    } else {
                        for ((i, vehicleId) in vIds.withIndex()) {
                            this.api!!.makeAuthRequest(
                                "POST",
                                "/fleets/${this.id}/drivers/${driverId}/vehicles/${vehicleId}",
                                data = mapOf(
                                    "expiresAt" to (vehiclesExpiresAt[i])
                                )
                            )
                            return driver
                        }
                    }
                }
            } catch (e: Exception) {
                // equivalent to a rollback
                this.api!!.makeAuthRequest(
                    "DELETE", "/fleets/${this.id}/drivers/${driverId}"

                )
            }
            return driver
        }
        return null
    }


    /**
     * Remove a driver from the fleet
     *
     * @param driverId The driver ID
     */
    suspend fun removeDriver(driverId: String) {

        this.api!!.makeAuthRequest("DELETE", "/fleets/${this.id}/drivers/{$driverId}")


    }

    /**
     * Get a driver from the fleet
     *
     * @param driverId The driver ID
     * @return
     */
    suspend fun getDriver(driverId: String): FleetDriver {
        val driverRes = this.api!!.makeAuthRequest("POST", "/fleets/${this.id}/drivers/${driverId}")

        var driver = Json.decodeFromString<FleetDriver>(driverRes.readText())

        return driver

    }

    /**
     * Update driver
     *
     * @param driverId
     * @param isVehicleManager
     * @param isDriverManager
     * @param isBillingManager
     * @param expiresAt
     * @param isActive
     * @return
     */
    suspend fun updateDriver(
        driverId: String,
        isVehicleManager: Boolean = false,
        isDriverManager: Boolean = false,
        isBillingManager: Boolean = false,
        expiresAt: OffsetDateTime? = null,
        isActive: Boolean = true
    ): FleetDriver {
        val data = mutableMapOf<String, Any?>(
            "driverId" to driverId,
            "isVehicleManager" to isVehicleManager,
            "isDriverManager" to isDriverManager,
            "isBillingManager" to isBillingManager,
            // TODO iso format equiv?
            "expiresAt" to expiresAt,
            "isActive" to isActive
        )

        val driverRes = this.api!!.makeAuthRequest(
            "PATCH",
            "/fleets/${this.id}/drivers/${driverId}",
            data = data
        )

        val driver = Json.decodeFromString<FleetDriver>(driverRes.readText())
        return driver
    }

    /**
     * List drivers
     *
     * @return
     */
    suspend fun listDrivers(): Flow<FleetDriver> = flow {
        val limit = 50
        this@Fleet.api?.batchFetch("fleets/${this@Fleet.id}/drivers")?.collect { res ->
            val fleetDriver: FleetDriver = Json.decodeFromJsonElement(res)

            fleetDriver.api = this@Fleet.api
            emit(fleetDriver)
        }
    }


    /**
     * Add a vehicle to the fleet
     *
     * @param vehicleId The vehicle ID
     * @param isActive If active in the fleet. Defaults to true.
     * @param isOpenToAll If open to all drivers. Defaults to true.
     * @return
     */
    suspend fun addVehicle(
        vehicleId: String,
        isActive: Boolean = true,
        isOpenToAll: Boolean = true
    ): FleetVehicle {
        val data = mutableMapOf<String, Any?>(
            "isOpenToAll" to isOpenToAll,
            "isActive" to isActive,
            "registeredVehicleId" to vehicleId
        )
        val res = this.api!!.makeAuthRequest("POST", "/fleets/${this.id}/vehicles/", data = data)

        return Json.decodeFromString(res.readText())
    }

    /**
     * Remove a vehicle from the fleet
     *
     * @param vehicleId The vehicle ID
     */
    suspend fun removeVehicle(vehicleId: String) {
        val res = this.api!!.makeAuthRequest("DELETE", "/fleets/${this.id}/vehicles/$vehicleId")

    }

    /**
     * Update a vehicle assignment
     *
     * @param vehicleId The vehicle ID.
     * @param isActive If active in the fleet. Defaults to true.
     * @param isOpenToAll If open to all drivers. Defaults to true.
     * @return
     */
    suspend fun updateVehicle(
        vehicleId: String,
        isActive: Boolean = true,
        isOpenToAll: Boolean = true
    ): FleetVehicle {
        val data = mutableMapOf<String, Any?>(
            "isOpenToAll" to isOpenToAll,
            "isActive" to isActive
        )

        val res = this.api!!.makeAuthRequest(
            "PATCH",
            "/fleets/${this.id}/vehicles/$vehicleId",
            data = data
        )

        var vehicle = Json.decodeFromString<FleetVehicle>(res.readText())
        return vehicle
    }

    /**
     * List the vehicles in the fleet.
     *
     * @return
     */
    suspend fun listVehicles(): Flow<FleetVehicle> = flow {
        val limit = 50
        this@Fleet.api?.batchFetch("fleets/${this@Fleet.id}/vehicles")?.collect { res ->
            val fleetVehicle: FleetVehicle = Json.decodeFromJsonElement(res)
            fleetVehicle.api = this@Fleet.api
            emit(fleetVehicle)
        }
    }


    /**
     * Add a driver to a vehicle
     *
     * @param driverId The driver ID
     * @param vehicleId The vehicle ID
     * @param expiresAt If and when the driver assignment expires. Defaults to None
     * @param isActive If active in the fleet. Defaults to True.
     * @return
     */
    suspend fun addDriverToVehicle(
        driverId: String,
        vehicleId: String,
        expiresAt: OffsetDateTime? = null,
        isActive: Boolean = true
    ): FleetDriverVehicleAssignment {
        val data = mutableMapOf<String, Any?>(
            "expiresAt" to expiresAt,
            "isActive" to isActive,
            "registeredVehicleId" to vehicleId
        )
        val res = this.api!!.makeAuthRequest(
            "POST",
            "/fleets/${this.id}/drivers/$driverId/vehicles/",
            data = data
        )

        return Json.decodeFromString<FleetDriverVehicleAssignment>(res.readText())

    }

    /**
     * List the driver to vehicle assignments in the fleet.
     *
     * @param driverId The driver ID
     * @param includeUnassigned Whether to include unassigned vehicles. Defaults to true.
     * @return
     */
    suspend fun listDriverVehicleAssignment(
        driverId: String,
        includeUnassigned: Boolean = true
    ): Flow<FleetDriverVehicleAssignment> = flow {
        val params: MutableMap<String, String?> =
            mutableMapOf<String, String?>(
                "limit" to "50",
                "includeUnassigned" to includeUnassigned.toString()
            )

        this@Fleet.api?.batchFetch(
            "fleets/${this@Fleet.id}/drivers/${driverId}/vehicles",
            params
        )?.collect { res ->
            val fleetDriverVehicleAssignment: FleetDriverVehicleAssignment =
                Json.decodeFromJsonElement(res)
            fleetDriverVehicleAssignment.api = this@Fleet.api
            emit(fleetDriverVehicleAssignment)
        }
    }

    /**
     * Remove a driver from a vehicle
     *
     * @param driverId The driver ID
     * @param vehicleId The vehicle ID
     */
    suspend fun removeDriverFromVehicle(driverId: String, vehicleId: String) {
        val res = this.api!!.makeAuthRequest(
            "DELETE",
            "/fleets/${this.id}/drivers/$driverId/vehicles/$vehicleId"
        )
    }

    /**
     * Update driver vehicle assignment
     *
     * @param driverId The driver ID
     * @param vehicleId The vehicle ID
     * @param expiresAt If and when the driver assignment expires. Defaults to None
     * @param isActive If active in the fleet. Defaults to True.
     * @return
     */
    suspend fun updateDriverVehicleAssignment(
        driverId: String,
        vehicleId: String,
        expiresAt: OffsetDateTime? = null,
        isActive: Boolean = true
    ): FleetDriverVehicleAssignment {
        val data = mutableMapOf<String, Any?>(
            "expiresAt" to expiresAt,
            "isActive" to isActive
        )
        val res = this.api!!.makeAuthRequest(
            "PATCH",
            "/fleets/${this.id}/vehicles/$vehicleId",
            data = data
        )

        return Json.decodeFromString<FleetDriverVehicleAssignment>(res.readText())
    }


    /**
     * List all policies for this fleet.
     *
     * @return
     */

    suspend fun listPolicies(): Flow<Policy> = flow {
        /*List all policies for this fleet*/
        // TODO Check if Fleetsids should be an array
        val params: MutableMap<String, String?> =
            mutableMapOf<String, String?>("limit" to "50", "fleetIds" to this@Fleet.id.toString())

        this@Fleet.api?.batchFetch("policy", params, null)?.collect { res ->
            val policy: Policy = Json.decodeFromJsonElement(res)
            policy.api = this@Fleet.api
            emit(policy)
        }
    }
}
