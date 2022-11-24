package com.inaza.androidsdk.models.vehicles

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import com.inaza.androidsdk.models.Translations
import com.inaza.androidsdk.models.custom.PrivateApiHandler
import com.inaza.androidsdk.models.risk.CommonRisk
import com.inaza.androidsdk.models.risk.Risk
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.OffsetDateTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Vehicle type
 *
 * @property api
 * @property externalId
 * @property display Text field for display purposes.
 * @property description Extended text field for a description of the vehicle.
 * @property isActive If this vehicle is active. If not, it will not be returned from the API unless requested.
 * @property vehicleType The vehicle type/category. For a list of all available types, see path: GET /variables/vehicle-types
 * @property variant A descriptive field for this variant of the make and model, eg. sedan, SUV.
 * @property code An additional code tied to the vehicle for internal idenification. This could be a manufacturer's code or risk system.
 * @property baseMsrpNew The base manufacturer's suggested retail price (MSRP) when new.
 * @property baseMsrpCurrent The base manufacturer's suggested retail price (MSRP) when not new (preowned).
 * @property brand The vehicle brand.
This field is highly important when a driver is selecting their vehicle.
Make sure all fields have the same case, ie. Audi vs. audi. Otherwise it will be seen as differing brands.
 * @property model The vehicle brand model.
This field is highly important when a driver is selecting their vehicle.
Make sure all fields have the same case, ie. A4 vs. a4. Otherwise it will be seen as differing models.
 * @property yearFloor The first year of production for this vehicle.
 * @property yearTop The final year of production for this vehicle.
 * @property doors The number of doors on this vehicle.
 * @property wheels The number of wheels on this vehicle.
 * @property seats The number of seats in this vehicle.
 * @property cylinders The number of cylinders in this vehicles engine.
 * @property valves The number of valves in this vehicles engine.
 * @property valveTiming Text field descriptor of valve timing.
 * @property camType
 * @property driveType
 * @property transmission
 * @property gearCount
 * @property engineSizeML
 * @property horsePower
 * @property torqueNM
 * @property engineType
 * @property fuelType
 * @property fuelTankCapacityML
 * @property combinedKMPL
 * @property cityKMPL
 * @property combinedKMPLE
 * @property cityKMPLE
 * @property kwh100KM
 * @property timeToCharge240VMins
 * @property electricRangeKM
 * @property warrantyBasicYears
 * @property warrantyBasicKM
 * @property warrantyBasicExpiry
 * @property warrantyDrivetrainYears
 * @property warrantyDrivetrainKM
 * @property warrantyDrivetrainExpiry
 * @property warrantyRoadsideYears
 * @property warrantyRoadsideKM
 * @property warrantyRoadsideExpiry
 * @property warrantyRustYears
 * @property warrantyRustKM
 * @property warrantyRustExpiry
 * @property warrantyFreeMaintenanceYears
 * @property warrantyFreeMaintenanceKM
 * @property warrantyFreeMaintenanceExpiry
 * @property warrantyHybridComponentYears
 * @property warrantyHybridComponentKM
 * @property warrantyHybridComponentExpiry
 * @property warrantyEVBatteryYears
 * @property warrantyEVBatteryKM
 * @property warrantyEVBatteryExpiry
 * @property lengthMM
 * @property widthMM
 * @property heightMM
 * @property wheelBaseMM
 * @property frontTrackMM
 * @property rearTrackMM
 * @property groundClearanceMM
 * @property angleOfApproachDegrees
 * @property angleOfDepartureDegrees
 * @property turningCircleM
 * @property dragCoefficient
 * @property epaInteriorVolumeM3
 * @property cargoCapacityM3
 * @property maxCargoCapacityM3
 * @property curbWeightKG
 * @property grossWeightKG
 * @property maxPayloadKG
 * @property maxTowingCapacityKG
 * @property id
 * @property createdAt
 * @property sourceID
 * @property imageLOC
 * @property thumbnailLOC
 * @property translations
 * @property risk
 * @constructor Create empty Vehicle type
 */
@Serializable
data class VehicleType(
    @Transient
    override val api: ApiHandler? = null,
    @SerialName("externalId")
    private val _externalId: String? = null,

    @SerialName("display")
    private val _display: String? = null,

    @SerialName("description")
    private val _description: String? = null,

    @SerialName("isActive")
    private val _isActive: Boolean? = null,

    @SerialName("vehicleType")
    private val _vehicleType: VehicleCategory? = null,

    @SerialName("variant")
    private val _variant: String? = null,

    @SerialName("code")
    private val _code: String? = null,

    @SerialName("baseMsrpNew")
    private val _baseMsrpNew: Float? = null,

    @SerialName("baseMsrpCurrent")
    private val _baseMsrpCurrent: Float? = null,

    @SerialName("brand")
    private val _brand: String? = null,

    @SerialName("model")
    private val _model: String? = null,

    @SerialName("yearFloor")
    private val _yearFloor: Int? = null,

    @SerialName("yearTop")
    private val _yearTop: Int? = null,

    @SerialName("doors")
    private val _doors: Int? = null,

    @SerialName("wheels")
    private val _wheels: Int? = null,

    @SerialName("seats")
    private val _seats: Int? = null,

    @SerialName("cylinders")
    private val _cylinders: Int? = null,

    @SerialName("valves")
    private val _valves: Int? = null,

    @SerialName("valveTiming")
    private val _valveTiming: String? = null,

    @SerialName("camType")
    private val _camType: String? = null,

    @SerialName("driveType")
    private val _driveType: String? = null,

    @SerialName("transmission")
    private val _transmission: String? = null,

    @SerialName("gearCount")
    private val _gearCount: Int? = null,

    @SerialName("engineSizeML")
    private val _engineSizeML: Int? = null,

    @SerialName("horsePower")
    private val _horsePower: Int? = null,

    @SerialName("torqueNM")
    private val _torqueNM: Float? = null,

    @SerialName("engineType")
    private val _engineType: String? = null,

    @SerialName("fuelType")
    private val _fuelType: String? = null,

    @SerialName("fuelTankCapacityML")
    private val _fuelTankCapacityML: Int? = null,

    @SerialName("_combinedKMPL")
    private val _combinedKMPL: Float? = null,

    @SerialName("cityKMPL")
    private val _cityKMPL: Float? = null,

    @SerialName("combinedKMPLE")
    private val _combinedKMPLE: Float? = null,

    @SerialName("cityKMPLE")
    private val _cityKMPLE: Float? = null,

    @SerialName("kwh100KM")
    private val _kwh100KM: Float? = null,

    @SerialName("timeToCharge240VMins")
    private val _timeToCharge240VMins: Float? = null,

    @SerialName("electricRangeKM")
    private val _electricRangeKM: Float? = null,

    @SerialName("warrantyBasicYears")
    private val _warrantyBasicYears: Int? = null,

    @SerialName("warrantyBasicKM")
    private val _warrantyBasicKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyBasicExpiry")
    private val _warrantyBasicExpiry: OffsetDateTime? = null,

    @SerialName("warrantyDrivetrainYears")
    private val _warrantyDrivetrainYears: Int? = null,

    @SerialName("warrantyDrivetrainKM")
    private val _warrantyDrivetrainKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyDrivetrainExpiry")
    private val _warrantyDrivetrainExpiry: OffsetDateTime? = null,

    @SerialName("warrantyRoadsideYears")
    private val _warrantyRoadsideYears: Int? = null,

    @SerialName("warrantyRoadsideKM")
    private val _warrantyRoadsideKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyRoadsideExpiry")
    private val _warrantyRoadsideExpiry: OffsetDateTime? = null,

    @SerialName("warrantyRustYears")
    private val _warrantyRustYears: Int? = null,

    @SerialName("warrantyRustKM")
    private val _warrantyRustKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyRustExpiry")
    private val _warrantyRustExpiry: OffsetDateTime? = null,

    @SerialName("warrantyFreeMaintenanceYears")
    private val _warrantyFreeMaintenanceYears: Int? = null,

    @SerialName("warrantyFreeMaintenanceKM")
    private val _warrantyFreeMaintenanceKM: Int? = null,

    @SerialName("warrantyFreeMaintenanceExpiry")
    private val _warrantyFreeMaintenanceExpiry: Int? = null,

    @SerialName("warrantyHybridComponentYears")
    private val _warrantyHybridComponentYears: Int? = null,

    @SerialName("warrantyHybridComponentKM")
    private val _warrantyHybridComponentKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyHybridComponentExpiry")
    private val _warrantyHybridComponentExpiry: OffsetDateTime? = null,

    @SerialName("warrantyEVBatteryYears")
    private val _warrantyEVBatteryYears: Int? = null,

    @SerialName("warrantyEVBatteryKM")
    private val _warrantyEVBatteryKM: Int? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("warrantyEVBatteryExpiry")
    private val _warrantyEVBatteryExpiry: OffsetDateTime? = null,

    @SerialName("lengthMM")
    private val _lengthMM: Int? = null,

    @SerialName("widthMM")
    private val _widthMM: Int? = null,

    @SerialName("heightMM")
    private val _heightMM: Int? = null,

    @SerialName("wheelBaseMM")
    private val _wheelBaseMM: Int? = null,

    @SerialName("frontTrackMM")
    private val _frontTrackMM: Int? = null,

    @SerialName("rearTrackMM")
    private val _rearTrackMM: Int? = null,

    @SerialName("groundClearanceMM")
    private val _groundClearanceMM: Int? = null,

    @SerialName("angleOfApproachDegrees")
    private val _angleOfApproachDegrees: Float? = null,

    @SerialName("angleOfDepartureDegrees")
    private val _angleOfDepartureDegrees: Float? = null,

    @SerialName("turningCircleM")
    private val _turningCircleM: Float? = null,

    @SerialName("dragCoefficient")
    private val _dragCoefficient: Float? = null,

    @SerialName("epaInteriorVolumeM3")
    private val _epaInteriorVolumeM3: Float? = null,

    @SerialName("cargoCapacityM3")
    private val _cargoCapacityM3: Float? = null,

    @SerialName("maxCargoCapacityM3")
    private val _maxCargoCapacityM3: Float? = null,

    @SerialName("curbWeightKG")
    private val _curbWeightKG: Float? = null,

    @SerialName("grossWeightKG")
    private val _grossWeightKG: Float? = null,

    @SerialName("maxPayloadKG")
    private val _maxPayloadKG: Float? = null,

    @SerialName("maxTowingCapacityKG")
    private val _maxTowingCapacityKG: Float? = null,

    @SerialName("id")
    private val _id: String? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("_createdAt")
    private val _createdAt: OffsetDateTime? = null,

    @SerialName("sourceId")
    private val _sourceID: String? = null,

    @SerialName("imageLoc")
    private val _imageLOC: String? = null,

    @SerialName("thumbnailLoc")
    private val _thumbnailLOC: String? = null,

    @SerialName("translations")
    private val _translations: Translations? = null,

    @SerialName("risk")
    private val _risk: Risk? = null

) : PrivateApiHandler() {
    @Transient
    var changedFields = mutableMapOf<String, Any?>()


    var externalId: String? by Delegates.observable(_externalId, ::watcher)
    var display: String? by Delegates.observable(_display, ::watcher)
    var description: String? by Delegates.observable(_description, ::watcher)
    var isActive: Boolean? by Delegates.observable(_isActive, ::watcher)
    var vehicleType: VehicleCategory? by Delegates.observable(_vehicleType, ::watcher)
    var variant: String? by Delegates.observable(_variant, ::watcher)
    var code: String? by Delegates.observable(_code, ::watcher)
    var baseMsrpNew: Float? by Delegates.observable(_baseMsrpNew, ::watcher)
    var baseMsrpCurrent: Float? by Delegates.observable(_baseMsrpCurrent, ::watcher)
    var brand: String? by Delegates.observable(_brand, ::watcher)
    var model: String? by Delegates.observable(_model, ::watcher)
    var yearFloor: Int? by Delegates.observable(_yearFloor, ::watcher)
    var yearTop: Int? by Delegates.observable(_yearTop, ::watcher)
    var doors: Int? by Delegates.observable(_doors, ::watcher)
    var wheels: Int? by Delegates.observable(_wheels, ::watcher)
    var seats: Int? by Delegates.observable(_seats, ::watcher)
    var cylinders: Int? by Delegates.observable(_cylinders, ::watcher)
    var valves: Int? by Delegates.observable(_valves, ::watcher)
    var valveTiming: String? by Delegates.observable(_valveTiming, ::watcher)
    var camType: String? by Delegates.observable(_camType, ::watcher)
    var driveType: String? by Delegates.observable(_driveType, ::watcher)
    var transmission: String? by Delegates.observable(_transmission, ::watcher)
    var gearCount: Int? by Delegates.observable(_gearCount, ::watcher)
    var engineSizeML: Int? by Delegates.observable(_engineSizeML, ::watcher)
    var horsePower: Int? by Delegates.observable(_horsePower, ::watcher)
    var torqueNM: Float? by Delegates.observable(_torqueNM, ::watcher)
    var engineType: String? by Delegates.observable(_engineType, ::watcher)
    var fuelType: String? by Delegates.observable(_fuelType, ::watcher)
    var fuelTankCapacityML: Int? by Delegates.observable(_fuelTankCapacityML, ::watcher)
    var combinedKMPL: Float? by Delegates.observable(_combinedKMPL, ::watcher)
    var cityKMPL: Float? by Delegates.observable(_cityKMPL, ::watcher)
    var combinedKMPLE: Float? by Delegates.observable(_combinedKMPLE, ::watcher)
    var cityKMPLE: Float? by Delegates.observable(_cityKMPLE, ::watcher)
    var kwh100KM: Float? by Delegates.observable(_kwh100KM, ::watcher)

    var timeToCharge240VMins: Float? by Delegates.observable(_timeToCharge240VMins, ::watcher)

    var electricRangeKM: Float? by Delegates.observable(_electricRangeKM, ::watcher)
    var warrantyBasicYears: Int? by Delegates.observable(_warrantyBasicYears, ::watcher)
    var warrantyBasicKM: Int? by Delegates.observable(_warrantyBasicKM, ::watcher)
    var warrantyBasicExpiry: OffsetDateTime? by Delegates.observable(
        _warrantyBasicExpiry,
        ::watcher
    )
    var warrantyDrivetrainYears: Int? by Delegates.observable(_warrantyDrivetrainYears, ::watcher)
    var warrantyDrivetrainKM: Int? by Delegates.observable(_warrantyDrivetrainKM, ::watcher)
    var warrantyDrivetrainExpiry: OffsetDateTime? by Delegates.observable(
        _warrantyDrivetrainExpiry,
        ::watcher
    )
    var warrantyRoadsideYears: Int? by Delegates.observable(_warrantyRoadsideYears, ::watcher)
    var warrantyRoadsideKM: Int? by Delegates.observable(_warrantyRoadsideKM, ::watcher)
    var warrantyRoadsideExpiry: OffsetDateTime? by Delegates.observable(
        _warrantyRoadsideExpiry,
        ::watcher
    )
    var warrantyRustYears: Int? by Delegates.observable(_warrantyRustYears, ::watcher)
    var warrantyRustKM: Int? by Delegates.observable(_warrantyRustKM, ::watcher)
    var warrantyRustExpiry: OffsetDateTime? by Delegates.observable(_warrantyRustExpiry, ::watcher)
    var warrantyFreeMaintenanceYears: Int? by Delegates.observable(
        _warrantyFreeMaintenanceYears,
        ::watcher
    )
    var warrantyFreeMaintenanceKM: Int? by Delegates.observable(
        _warrantyFreeMaintenanceKM,
        ::watcher
    )
    var warrantyFreeMaintenanceExpiry: Int? by Delegates.observable(
        _warrantyFreeMaintenanceExpiry,
        ::watcher
    )
    var warrantyHybridComponentYears: Int? by Delegates.observable(
        _warrantyHybridComponentYears,
        ::watcher
    )
    var warrantyHybridComponentKM: Int? by Delegates.observable(
        _warrantyHybridComponentKM,
        ::watcher
    )
    var warrantyHybridComponentExpiry: OffsetDateTime? by Delegates.observable(
        _warrantyHybridComponentExpiry,
        ::watcher
    )
    var warrantyEVBatteryYears: Int? by Delegates.observable(_warrantyEVBatteryYears, ::watcher)
    var warrantyEVBatteryKM: Int? by Delegates.observable(_warrantyEVBatteryKM, ::watcher)
    var warrantyEVBatteryExpiry: OffsetDateTime? by Delegates.observable(
        _warrantyEVBatteryExpiry,
        ::watcher
    )
    var lengthMM: Int? by Delegates.observable(_lengthMM, ::watcher)
    var widthMM: Int? by Delegates.observable(_widthMM, ::watcher)
    var heightMM: Int? by Delegates.observable(_heightMM, ::watcher)
    var wheelBaseMM: Int? by Delegates.observable(_wheelBaseMM, ::watcher)
    var frontTrackMM: Int? by Delegates.observable(_frontTrackMM, ::watcher)
    var rearTrackMM: Int? by Delegates.observable(_rearTrackMM, ::watcher)
    var groundClearanceMM: Int? by Delegates.observable(_groundClearanceMM, ::watcher)
    var angleOfApproachDegrees: Float? by Delegates.observable(_angleOfApproachDegrees, ::watcher)
    var angleOfDepartureDegrees: Float? by Delegates.observable(_angleOfDepartureDegrees, ::watcher)
    var turningCircleM: Float? by Delegates.observable(_turningCircleM, ::watcher)
    var dragCoefficient: Float? by Delegates.observable(_dragCoefficient, ::watcher)
    var epaInteriorVolumeM3: Float? by Delegates.observable(_epaInteriorVolumeM3, ::watcher)
    var cargoCapacityM3: Float? by Delegates.observable(_cargoCapacityM3, ::watcher)
    var maxCargoCapacityM3: Float? by Delegates.observable(_maxCargoCapacityM3, ::watcher)
    var curbWeightKG: Float? by Delegates.observable(_curbWeightKG, ::watcher)
    var grossWeightKG: Float? by Delegates.observable(_grossWeightKG, ::watcher)
    var maxPayloadKG: Float? by Delegates.observable(_maxPayloadKG, ::watcher)
    var maxTowingCapacityKG: Float? by Delegates.observable(_maxTowingCapacityKG, ::watcher)
    val id: String? by Delegates.observable(_id, ::watcher)
    val createdAt: OffsetDateTime? by Delegates.observable(_createdAt, ::watcher)

    var sourceID: String? by Delegates.observable(_sourceID, ::watcher)

    var imageLOC: String? by Delegates.observable(_imageLOC, ::watcher)

    var thumbnailLOC: String? by Delegates.observable(_thumbnailLOC, ::watcher)

    var translations: Translations? by Delegates.observable(_translations, ::watcher)
    var risk: Risk? by Delegates.observable(_risk, ::watcher)


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
     * A simple display string to identify the model to the user.
     *
     * @return String
     */
    suspend fun getDisplay(): String {
        return "${this.brand} - ${this.model}"
    }

    /**
     * Refresh the model from the API.
     *
     */
    suspend fun refresh() {
        this.checkId()


        val refresh: VehicleType =
            Json.decodeFromString(
                this.api!!.makeAuthRequest("GET", "/vehicles/${this.id}").readText()
            )

        for (p in VehicleType::class.memberProperties) {
            val prop = p.name

            val value = VehicleType::class.memberProperties
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
        this.api!!.makeAuthRequest("DELETE", "/vehicles/${this.id}")

    }

    /**
     * Persist any changes in the API.
     *
     * @param fields the API formatted fields to update. If not supplied, any set fields in the model will be updated in the API. Defaults to None.
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        /*Persist any local changes in the API*/
        val f = fields ?: this.changedFields
        this.modelSave("/vehicles/${this.id}", f, setOf("vehicleType"))
    }

    /**
     * Update a field on the model, call save or keyword persist to persist changes in the API.
     *
     * @param persist whether to persist the changes to the API. Defaults to False.
     * @param fields the API formatted fields to update. If not supplied, any set fields in the model will be updated in the API. Defaults to None.
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        /*Update a field on the model, call save() or keyword persist to persist changes in the API.*/
        this.modelUpdate("/-vehicles/${this.id}", persist, fields)
    }
}


/**
 * Vehicle category
 *
 * @constructor
 *
 * @param type
 */
enum class VehicleCategory(type: String) {
    /**
     * Automobile
     *
     * @constructor Create empty Automobile
     */
    AUTOMOBILE("automobile"),

    /**
     * Motorcycle
     *
     * @constructor Create empty Motorcycle
     */
    MOTORCYCLE("motorcycle"),

    /**
     * Atv
     *
     * @constructor Create empty Atv
     */
    ATV("atv"),

    /**
     * Rail
     *
     * @constructor Create empty Rail
     */
    RAIL("rail"),

    /**
     * Transport
     *
     * @constructor Create empty Transport
     */
    TRANSPORT("transport"),

    /**
     * Aircraft
     *
     * @constructor Create empty Aircraft
     */
    AIRCRAFT("aircraft"),

    /**
     * Seacraft
     *
     * @constructor Create empty Seacraft
     */
    SEACRAFT("seacraft"),

    /**
     * Towed
     *
     * @constructor Create empty Towed
     */
    TOWED("towed"),

    /**
     * Tractor
     *
     * @constructor Create empty Tractor
     */
    TRACTOR("tractor"),

    /**
     * Commercial
     *
     * @constructor Create empty Commercial
     */
    COMMERCIAL("commercial"),

    /**
     * Military
     *
     * @constructor Create empty Military
     */
    MILITARY("military"),

    /**
     * Special
     *
     * @constructor Create empty Special
     */
    SPECIAL("special"),

    /**
     * Unknown
     *
     * @constructor Create empty Unknown
     */
    UNKNOWN("unknown"),

    /**
     * Misc
     *
     * @constructor Create empty Misc
     */
    MISC("misc")
}
