package com.example.inazaandroidsdk.models.vehicles

import api.ApiHandler
import com.example.inazaandroidsdk.helpers.checkApiHandler
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import models.custom.PrivateApiHandler
import models.policy.Policy
import models.policy.enums.PolicyGroup
import models.risk.Risk
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Vehicle
 *
 * @property api API object used to connect to the Inaza API
 * @property externalId wfwfw
 * @property regPlate The vehicle registration plate number.
 * @property vin The vehicle identification number.
 * @property year The year this vehicle was produced.
 * @property preowned If this vehicle has had previous owner(s).
 * @property isApproved If this vehicle has been approved to be insured internally.
 * @property approvedAt The ISO timestamp of when this vehicle was approved internally.
 * @property onRoadParking If this vehicle is parked on an active roadway.
 * @property mileageKm The most recent odometer reading in KM.
 * @property engineLitres The engine capacity in litres. This value overrides the inherited vehicle value.
 * @property fuelType The vehicle fuel type. This value overrides the inherited vehicle value.
 * @property hasTurbo If this vehicle has been modified to have a turbocharger.
 * @property hasSupercharger If this vehicle has been modified to have a supercharger.
 * @property bodyModified If the body of this vehicle has been modified from its stock option.
 * @property engineModified If the engine of this vehicle has been modified from its stock option.
 * @property gearboxType The gearbox type. This value overrides the inherited vehicle value.
 * @property isActive If this vehicle is active. If false, this vehicle will not be returned to the end user.
 * @property risk MISSING?
 * @property id The unique ID of this registered vehicle.
 * @property sourceId The source ID (telematics tracking ID).This source ID type is often used in fleet management.
Any billing events tied to this ID will be charged back to a driver through a DRV, or to the attached fleet.

> Contact nSurely to see what source ID is best for your use-case.
 * @property createdAt When this RV was added to the system.
 * @property frontPicLoc Front Image of Vehicle - File
 * @property sidePicLoc1 Side Image of Vehicle (1 of 2) - File
 * @property sidePicLoc2 Side Image of Vehicle (2 of 2) - File
 * @property rearPicLoc Rear Image of Vehicle - File
 * @property topPicLoc Top Image of Vehicle - File
 * @property proofOfRegLoc Scan of proof of registration of this vehicle. For any operations on this file, use the `/file*` routes.
 * @property distance3m The distance in KM from the last 3 months, including all DRV's.
 * @property totalDrvCount The total number of DRV's that have been associated with this vehicle.
 * @property policies TODO MISSING?
 * @property vehicle The vehicle type.
 * @property driverVehicles MISSING?
 * @constructor Create empty Vehicle
 */
@Serializable
class Vehicle(
    @Transient
    override var api: ApiHandler? = null,

    @SerialName("externalId") private var _externalId: String? = null,
    @SerialName("regPlate") private var _regPlate: String? = null,
    @SerialName("vehicleId") private var _vehicleId: String? = null,
    @SerialName("vin") private var _vin: String? = null,
    @SerialName("year") private var _year: Int? = null,
    @SerialName("preowned") private var _preowned: Boolean? = null,
    @SerialName("isApproved") private var _isApproved: Boolean? = null,
    @SerialName("approvedAt") private var _approvedAt: String? = null,
    @SerialName("onRoadParking") private var _onRoadParking: String? = null,
    @SerialName("mileageKm") private var _mileageKm: String? = null,
    @SerialName("engineLitres") private var _engineLitres: String? = null,
    @SerialName("fuelType") private var _fuelType: String? = null,
    @SerialName("hasTurbo") private var _hasTurbo: String? = null,
    @SerialName("hasSupercharger") private var _hasSupercharger: String? = null,
    @SerialName("bodyModified") private var _bodyModified: Boolean? = null,
    @SerialName("engineModified") private var _engineModified: String? = null,
    @SerialName("gearboxType") private var _gearboxType: String? = null,
    @SerialName("isActive") private var _isActive: Boolean? = null,
    @SerialName("risk") private var _risk: Risk? = Risk(),
    @SerialName("id") private var _id: String? = null,
    @SerialName("sourceId") private var _sourceId: String? = null,
    @SerialName("createdAt") private var _createdAt: String? = null,
    @SerialName("frontPicLoc") private var _frontPicLoc: String? = null,
    @SerialName("sidePicLoc1") private var _sidePicLoc1: String? = null,
    @SerialName("sidePicLoc2") private var _sidePicLoc2: String? = null,
    @SerialName("rearPicLoc") private var _rearPicLoc: String? = null,
    @SerialName("topPicLoc") private var _topPicLoc: String? = null,
    @SerialName("proofOfRegLoc") private var _proofOfRegLoc: String? = null,
    @SerialName("distance3m") private var _distance3m: Double? = null,
    @SerialName("totalDrvCount") private var _totalDrvCount: Int? = null,
    @SerialName("policies") private var _policies: List<Policy> = listOf<Policy>(),
    @SerialName("vehicle") private var _vehicle: VehicleType? = VehicleType(),
    // not in motorpy
    @SerialName("driverVehicles") private var _driverVehicles: List<DriverVehicle> = listOf(),
    @SerialName("backgroundCheckStatus") private var _backgroundCheckStatus: String? = null,
    @SerialName("backgroundCheckProvider") private var _backgroundCheckProvider: String? = null,
    @SerialName("backgroundCheckRawOutput") private var _backgroundCheckRawOutput: String? = null,
    @SerialName("backgroundCheckPassed") private var _backgroundCheckPassed: Boolean? = null,
) : PrivateApiHandler() {
    @kotlinx.serialization.Transient
    private var changedFields = mutableMapOf<String, Any?>()


    var externalId: String? by Delegates.observable(_externalId, ::watcher)

    var regPlate: String? by Delegates.observable(_regPlate, ::watcher)

    var vin: String? by Delegates.observable(_vin, ::watcher)

    var year: Int? by Delegates.observable(_year, ::watcher)

    var preowned: Boolean? by Delegates.observable(_preowned, ::watcher)

    var isApproved: Boolean? by Delegates.observable(_isApproved, ::watcher)

    var approvedAt: String? by Delegates.observable(_approvedAt, ::watcher)

    var onRoadParking: String? by Delegates.observable(_onRoadParking, ::watcher)

    var mileageKm: String? by Delegates.observable(_mileageKm, ::watcher)

    var engineLitres: String? by Delegates.observable(_engineLitres, ::watcher)

    var fuelType: String? by Delegates.observable(_fuelType, ::watcher)

    var hasTurbo: String? by Delegates.observable(_hasTurbo, ::watcher)

    var hasSupercharger: String? by Delegates.observable(_hasSupercharger, ::watcher)

    var bodyModified: Boolean? by Delegates.observable(_bodyModified, ::watcher)

    var engineModified: String? by Delegates.observable(_engineModified, ::watcher)

    var gearboxType: String? by Delegates.observable(_gearboxType, ::watcher)

    var isActive: Boolean? by Delegates.observable(_isActive, ::watcher)

    var risk: Risk? by Delegates.observable(_risk, ::watcher)

    var id: String? by Delegates.observable(_id, ::watcher)

    var sourceId: String? by Delegates.observable(_sourceId, ::watcher)

    var createdAt: String? by Delegates.observable(_createdAt, ::watcher)

    var frontPicLoc: String? by Delegates.observable(_frontPicLoc, ::watcher)

    var sidePicLoc1: String? by Delegates.observable(_sidePicLoc1, ::watcher)

    var sidePicLoc2: String? by Delegates.observable(_sidePicLoc2, ::watcher)

    var rearPicLoc: String? by Delegates.observable(_rearPicLoc, ::watcher)

    var topPicLoc: String? by Delegates.observable(_topPicLoc, ::watcher)

    var proofOfRegLoc: String? by Delegates.observable(_proofOfRegLoc, ::watcher)

    var vehicle: VehicleType? by Delegates.observable(_vehicle, ::watcher)

    var distance3m: Double? by Delegates.observable(_distance3m, ::watcher)

    var totalDrvCount: Int? by Delegates.observable(_totalDrvCount, ::watcher)
    var policies: List<Policy?> by Delegates.observable(_policies, ::watcher)
    var driverVehicles: List<DriverVehicle?> by Delegates.observable(_driverVehicles, ::watcher)
    var vehicleId: String? by Delegates.observable(_vehicleId, ::watcher)

    private fun watcher(prop: KProperty<*>, old: Any?, new: Any?) {
        if (old != new) {
            println("${prop.name} has changed from $old to $new")
            changedFields[prop.name] = new
        }
    }

    private suspend fun checkId() {
        if (this.id == null) {
            throw Exception("id must be set")
        }
    }

    /**
     * A simple display string to identify the model to the user
     *
     * @return
     */
    suspend fun getDisplay(): String {
        return "${this.vehicle?.getDisplay()} - this.regPlate}"
    }

    /**
     * TelematicsId
     *
     * @return Telematics Id as String
     */
    suspend fun telematicsId(): String? {
        /*Return the telematics ID.*/

        return this.sourceId
    }

    /**
     * List policies
     *
     * @param isActivePolicy Whether the policy is active or not
     * @return
     */
    suspend fun listPolicies(isActivePolicy: Boolean?): Flow<Policy> = flow {
        /*List policies for this vehicle*/
        val params: MutableMap<String, String?> = mutableMapOf("rvIds" to this@Vehicle.id)

        if (isActivePolicy != null) {
            params["isActivePolicy"] = isActivePolicy.toString()
        }

        this@Vehicle.api?.batchFetch("policy", params, null)
            ?.collect { res ->
                val policy: Policy = Json.decodeFromJsonElement(res)
                policy.api = this@Vehicle.api
                emit(policy)
            }
    }

    /**
     * Create policy for this registered vehicle.
     *
     * @param policy Pass an existing Policy object. If no value is supplied a default Policy will be generated.
     * @return Policy object
     */
    suspend fun createPolicy(policy: Policy? = null): Policy? {
        /*Create a policy for this vehicle.*/

        this.checkId()
        checkApiHandler(this.api)
        if (this.api == null) {
            throw Exception("api is required")
        }
        val p = policy ?: Policy(this.api)

        p.policyGroup = PolicyGroup.rv

        val pol = this.id?.let { p.create(apiHandler = this.api!!, recordId = it) }

        return pol
    }

    /**
     * Add a driver to this vehicle
     *
     * @param driverId Driver ID
     * @param drv Driver vehicle to add
     */
    suspend fun addDrv(driverId: String, drv: DriverVehicle): DriverVehicle {
        if (drv.api == null){
            drv.api = this.api
        }
        return withContext(Dispatchers.IO) { drv.create(driverId) }
    }

    /**
     * Add driver
     *
     * @param driverId Driver ID.
     * @param displayName Display name.
     * @param isOwner Whether the driver is the owner of the vehicle.
     * @param isPrimaryDriver Whether the driver is the primary driver of this vehicle.
     */
    suspend fun addDriver(driverId: String, displayName: String, isOwner: Boolean, isPrimaryDriver: Boolean): DriverVehicle {
        val drv = DriverVehicle(
            api = this.api,
            _displayName = displayName,
            _isOwner = isOwner,
            _isPrimaryDriver = isPrimaryDriver)

        return withContext(Dispatchers.IO) { this@Vehicle.addDrv(driverId, drv) }

    }

    /**
     * Sync the local model with the version present in the api.
     * Note: This will overwrite local changes.
     */
    suspend fun refresh() {
        this.checkId()


        val refresh: Vehicle =
            Json.decodeFromString(
                this.api!!.makeAuthRequest(
                    "GET",
                    "/registered-vehicles/${this.id}"
                ).readText()
            )

        for (p in Vehicle::class.memberProperties) {
            val prop = p.name

            val value = Vehicle::class.memberProperties
                .first { it.name == prop }
                .also { it.isAccessible = true } // Skip private properties
                .getter(refresh)

            this.setPropertyValue(prop, value)
        }
    }

    /**
     * Delete this registered vehicle through the API
     *
     */
    suspend fun delete() {
        this.checkId()
        this.api!!.makeAuthRequest("DELETE", "/drivers/${this.id}")

    }

    /**
     * Save the current local changes to this model through the API
     * Note: vehicleType cannot be changed
     *
     * @param fields
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        val f = fields ?: this.changedFields
        this.modelSave("/registered-vehicles/${this.id}", f, setOf("vehicleType"))
    }

    /**
     * Update the model properties
     *
     * @param persist If set to true it will update the model locally and through the api
     * @param fields Properties to be updated
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        this.modelUpdate("/registered-vehicles/${this.id}", persist, fields)
    }
}



