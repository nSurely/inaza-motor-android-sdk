package models.drivers

import api.ApiHandler
import com.example.inazaandroidsdk.helpers.checkApiHandler
import com.example.inazaandroidsdk.helpers.readPropertyOfAny
import helpers.KOffsetDateTimeSerializer
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import models.billing.BillingAccount
import models.billing.BillingEvent
import models.billing.BillingEventStatus
import models.billing.BillingEventType
import models.custom.PrivateApiHandler
import models.fleets.Fleet
import models.policy.Policy
import models.policy.enums.PolicyGroup
import models.risk.Risk
import com.example.inazaandroidsdk.models.vehicles.DriverVehicle
import kotlinx.serialization.json.decodeFromJsonElement
import java.time.OffsetDateTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


/**
 * The Driver object, representing a driver in the API.
 * Billing accounts, vehicles and more can be accessed via the Driver object.
 *
 * @property api
 * @property id
 * @property sourceId
 * @property externalId
 * @property firstName
 * @property middleName
 * @property lastName
 * @property gender
 * @property email
 * @property dob
 * @property phone
 * @property lang
 * @property drivingStartDate
 * @property occupation
 * @property apiPath
 * @property adrLine1
 * @property adrLine2
 * @property adrLine3
 * @property county
 * @property province
 * @property postcode
 * @property countryIsoCode
 * @property countryName
 * @property approvedAt
 * @property activationId
 * @property driverActivated
 * @property activatedAt
 * @property isApproved
 * @property isActive
 * @property driversLicenseLoc
 * @property proofOfAddressLoc
 * @property idLoc
 * @property profilePicLoc
 * @property vehicleCount
 * @property totalPoints
 * @property distanceKm30Days
 * @property createdAt
 * @property fleets
 * @property lastLogin
 * @property appLogin
 * @property appVersion
 * @property appDownloaded
 * @property risk
 * @constructor Create empty Driver
 */
@Serializable
class Driver(
    @Transient
    override var api: ApiHandler? = null,

    @SerialName("id")
    private val _id: String? = null,

    @SerialName("sourceId")
    private val _sourceId: String? = null,

    @SerialName("externalId")
    private val _externalId: String? = null,

    @SerialName("firstName")
    private val _firstName: String,

    @SerialName("middleName")
    private val _middleName: String? = null,

    @SerialName("lastName")
    private val _lastName: String,

    @SerialName("gender")
    private val _gender: String? = null,

    @SerialName("email")
    private val _email: String? = null,

    @SerialName("dob")
    private val _dob: LocalDate? = null,

    @SerialName("telE164")
    private val _phone: String? = null,

    @SerialName("lang")
    private val _lang: String? = null,

    @SerialName("drivingStartDate")
    private val _drivingStartDate: LocalDate? = null,

    @SerialName("occupation")
    private val _occupation: Occupation? = null,

    @SerialName("apiPath")
    private val _apiPath: String? = null,

    @SerialName("adrLine1")
    private val _adrLine1: String? = null,

    @SerialName("adrLine2")
    private val _adrLine2: String? = null,

    @SerialName("adrLine3")
    private val _adrLine3: String? = null,

    @SerialName("county")
    private val _county: String? = null,

    @SerialName("province")
    private val _province: String? = null,

    @SerialName("postcode")
    private val _postcode: String? = null,

    @SerialName("countryIsoCode")
    private val _countryIsoCode: String? = null,

    @SerialName("countryName")
    private val _countryName: String? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("approvedAt")
    private val _approvedAt: OffsetDateTime? = null,

    @SerialName("activationId")
    private val _activationId: String? = null,

    @SerialName("driverActivated")
    private val _driverActivated: Boolean? = null,

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("activatedAt")
    private val _activatedAt: OffsetDateTime? = null,

    @SerialName("isApproved")
    private val _isApproved: Boolean = false,

    @SerialName("isActive")
    private val _isActive: Boolean = false,

    @SerialName("driversLicenseLoc")
    private val _driversLicenseLoc: String? = null,

    @SerialName("proofOfAddressLoc")
    private val _proofOfAddressLoc: String? = null,

    @SerialName("idLoc")
    private val _idLoc: String? = null,

    @SerialName("profilePicLoc")
    private val _profilePicLoc: String? = null,

    @SerialName("vehicleCount")
    private val _vehicleCount: Int = 0,

    // meta
    @SerialName("totalPoints")
    private val _totalPoints: Int? = 0,

    @SerialName("distanceKm30Days")
    private val _distanceKm30Days: Int? = null,


    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("createdAt")
    private val _createdAt: OffsetDateTime? = null,

    @SerialName("fleets")
    private val _fleets: List<Fleet>? = null,
    // Try to inherit from CommonRisk

    @Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("lastLogin")
    private val _lastLogin: OffsetDateTime? = null,

    @SerialName("appLogin")
    private val _appLogin: Boolean? = null,

    @SerialName("appVersion")
    private val _appVersion: String? = null,

    @SerialName("appDownloaded")
    private val _appDownloaded: Boolean? = null,

    @SerialName("risk")
    private val _risk: Risk? = null
) : PrivateApiHandler() {
    @Transient
    var changedFields = mutableMapOf<String, Any?>()
    var id: String? by Delegates.observable(_id, ::watcher)
    var sourceId: String? by Delegates.observable(_sourceId, ::watcher)
    var externalId: String? by Delegates.observable(_externalId, ::watcher)
    var firstName: String by Delegates.observable(_firstName, ::watcher)
    var middleName: String? by Delegates.observable(_middleName, ::watcher)
    var lastName: String by Delegates.observable(_lastName, ::watcher)
    var gender: String? by Delegates.observable(_gender, ::watcher)
    var email: String? by Delegates.observable(_email, ::watcher)
    var phone: String? by Delegates.observable(_phone, ::watcher)
    var dob: LocalDate? by Delegates.observable(_dob, ::watcher)
    var lang: String? by Delegates.observable(_lang, ::watcher)
    var drivingStartDate: LocalDate? by Delegates.observable(_drivingStartDate, ::watcher)
    var occupation: Occupation? by Delegates.observable(_occupation, ::watcher)
    var apiPath: String? by Delegates.observable(_apiPath, ::watcher)
    var adrLine1: String? by Delegates.observable(_adrLine1, ::watcher)
    var adrLine2: String? by Delegates.observable(_adrLine2, ::watcher)
    var adrLine3: String? by Delegates.observable(_adrLine3, ::watcher)
    var county: String? by Delegates.observable(_county, ::watcher)
    var province: String? by Delegates.observable(_province, ::watcher)
    var postcode: String? by Delegates.observable(_postcode, ::watcher)
    var countryIsoCode: String? by Delegates.observable(_countryIsoCode, ::watcher)
    var countryName: String? by Delegates.observable(_countryName, ::watcher)
    var approvedAt: OffsetDateTime? by Delegates.observable(_approvedAt, ::watcher)
    var activationId: String? by Delegates.observable(_activationId, ::watcher)
    var driverActivated: Boolean? by Delegates.observable(_driverActivated, ::watcher)
    var activatedAt: OffsetDateTime? by Delegates.observable(_activatedAt, ::watcher)
    var isApproved: Boolean by Delegates.observable(_isApproved, ::watcher)
    var isActive: Boolean by Delegates.observable(_isActive, ::watcher)
    var driversLicenseLoc: String? by Delegates.observable(_driversLicenseLoc, ::watcher)
    var proofOfAddressLoc: String? by Delegates.observable(_proofOfAddressLoc, ::watcher)
    var idLoc: String? by Delegates.observable(_idLoc, ::watcher)
    var profilePicLoc: String? by Delegates.observable(_profilePicLoc, ::watcher)
    var vehicleCount: Int by Delegates.observable(_vehicleCount, ::watcher)
    var totalPoints: Int? by Delegates.observable(_totalPoints, ::watcher)
    var distanceKm30Days: Int? by Delegates.observable(_distanceKm30Days, ::watcher)
    var createdAt: OffsetDateTime? by Delegates.observable(_createdAt, ::watcher)
    var risk: Risk? by Delegates.observable(_risk, ::watcher)

    var fleets: List<Fleet>? by Delegates.observable(_fleets, ::watcher)

    var appLogin: Boolean? by Delegates.observable(_appLogin, ::watcher)

    var lastLogin: OffsetDateTime? by Delegates.observable(_lastLogin, ::watcher)
    var appVersion: String? by Delegates.observable(_appVersion, ::watcher)
    var appDownloaded: Boolean? by Delegates.observable(_appDownloaded, ::watcher)

    @kotlinx.serialization.Transient
    var vehiclesRaw: MutableList<DriverVehicle> = mutableListOf()


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
     * List all vehicles for this driver
     *
     * @return List[Vehicle]: list of vehicles
     */
    suspend fun listVehicles(): List<DriverVehicle> {
        val res = this.api?.makeAuthRequest("GET", "/drivers/${this.id}/vehicles")

        if (res != null) {
            this.vehiclesRaw = Json.decodeFromString(res.readText())
        }
        val l: MutableList<DriverVehicle> = mutableListOf()

        for (v in this.vehiclesRaw) {
            l.add(v)
        }
        return l

    }


    private suspend fun checkId() {
        if (this.id == null) {
            throw Exception("id must be set")
        }
    }

    /**
     * Get the lastest version of this model from the API.
     * Note: Local changes will be overwritten.
     *
     */
    suspend fun refresh() {
        this.checkId()

        val refresh: Driver = Json.decodeFromString(
            this.api!!.makeAuthRequest("GET", "drivers/${this.id}").readText()
        )
        for (p in Driver::class.memberProperties) {
            val prop = p.name

            val value = Driver::class.memberProperties
                .first { it.name == prop }
                .also { it.isAccessible = true } // Skip private properties
                .getter(refresh)
            this.setPropertyValue(prop, value)
        }
    }

    /**
     * Telematics id
     *
     * @return
     */
    suspend fun telematicsId(): String? {
        return this.sourceId
    }

    /**
     * Get the driver's full name
     *
     * @return
     */
    suspend fun fullName(): String {
        if (this.middleName != null) {
            return "%s %s %s".format(this.firstName, this.middleName, this.lastName)
        }
        return "%s %s".format(this.firstName, this.lastName)
    }

    /**
     * List billing accounts for this driver
     *
     * @param primaryOnly Boolean
     * @return List[BillingAccount]: billing accounts
     */
    suspend fun listBillingAccounts(primaryOnly: Boolean = false): List<BillingAccount> {
        this.checkId()
        val p: MutableMap<String, String?> = mutableMapOf()
        p["primary"] = primaryOnly.toString()

        val res = this.api?.makeAuthRequest("GET", "/drivers/${this.id}/billing-accounts", p)

        val billingAccounts: List<BillingAccount>? =
            res?.let { Json.decodeFromString(it.readText()) }

        if (billingAccounts != null) {
            for (b in billingAccounts) {
                b.api = this.api
            }
            return billingAccounts

        }
        return listOf()
    }

    /**
     * Create a billing account for this driver.
     *
     * @param account The account to create
     * @return
     */
    suspend fun createBillingAccount(account: BillingAccount): BillingAccount {
        this.checkId()
        checkApiHandler(this.api)

        this.api!!.makeAuthRequest("POST", "/drivers/${this.id}/billing-accounts", data=account)

        // TODO make optional and rest
        return BillingAccount(currencyISOCode = "EUR")
    }

    /**
     * Get a billing account for this driver.
     *
     * @param id The id of the billing account
     * @return
     */
    suspend fun getBillingAccount(id: String): BillingAccount? {
        this.checkId()

        if (id == null) {
            throw Exception("Billing id is required")
        }

        val ba = this.api?.makeAuthRequest("GET", "/drivers/${this.id}/billing-accounts/$id")

        if (ba != null) {
            return Json.decodeFromString<BillingAccount>(ba.readText())
        }
        return null
    }

    /**
     * Find the primary billing account for this driver.
     *
     * @return
     */
    suspend fun getPrimaryBillingAccount(): BillingAccount {
        val billingsAccounts = this.listBillingAccounts(primaryOnly = true)
        if (billingsAccounts.size == 0) {
            //TODO
            throw Exception("Driver has no billing account configured")
        }
        return billingsAccounts[0]

    }

    /**
     * List fleets for this driver.
     *
     * @return
     */
    suspend fun listFleets(): List<Fleet>? {
        if (this.fleets == null) {
            this.refresh()
        }

        return this.fleets

    }

    /**
     * Delete this record via the API.
     *
     */
    suspend fun delete() {
        this.checkId()
        this.api?.makeAuthRequest("DELETE", "/drivers/${this.id}")
    }

    /**
     * List vehicle policies for this driver.
     *
     * @param vehicleId Id of the vehicle.
     * @return
     */
    suspend fun listVehiclePolicies(vehicleId: String): List<Policy> {
        checkApiHandler(this.api)
        if (this.api != null) {

            val params: MutableMap<String, String?> = mutableMapOf("drvIds" to vehicleId)
            val response = this.api!!.makeAuthRequest("GET", "/policy", params, null, null)
            val policies: List<Policy> = Json.decodeFromString(response.readText())

            for (p in policies) {
                p.api = this.api
            }

            return policies
        }
        return listOf<Policy>()
    }

    /**
     * List policies for this driver.
     *
     * @param looseMatch If True, will return any policy related to the driver (D, DRV, RV, FD, FDRV). Defaults to True.
     * @param isActivePolicy If True, will return only active policies. Defaults to None.
     * @return
     */
    suspend fun listPolicies(
        looseMatch: Boolean = true,
        isActivePolicy: Boolean? = null
    ): Flow<Policy> = flow {
        var params = mutableMapOf<String, String?>()
        checkApiHandler(this@Driver.api)
        params["driverIds"] = this@Driver.id
        params["driverLooseMatch"] = looseMatch.toString()

        if (isActivePolicy != null) {
            params["isActivePolicy"] = isActivePolicy.toString()
        }

        this@Driver.api?.batchFetch("policy", params, null)?.collect { res ->
            val policy: Policy = Json.decodeFromJsonElement(res)
            policy.api = this@Driver.api
            emit(policy)
        }
    }

    /**
     * Create a policy for this driver.
     *
     * @param policy Policy to create. This can be left None and a new policy will be created using the org defaults.
     */
    suspend fun createPolicy(policy: Policy? = null) {
        var pol: Policy = policy ?: Policy(api = this.api)

        pol.policyGroup = PolicyGroup.d
        checkId()
        val res = this.api?.let { pol.create(it, this.id!!) }

        return
    }

    /**
     * Charge the driver. The billing event will be entered under their current primary billing account.
     *
     * @param amount The amount to charge. Defaults to null.
     * @param event The billing event to charge. This overrides the amount if both are provided. Defaults to null.
     * @return
     */
    suspend fun charge(amount: Int? = null, event: BillingEvent? = null): BillingEvent {
        if (amount == null && event == null){
            throw Exception("Either the amount or event is required")
        }
        // TODO: message or description?
        val evnt = event ?: amount?.let { BillingEvent(amount = it, message = "Charge", type = BillingEventType.other) }

        checkApiHandler(this.api)
        this.api!!.makeAuthRequest("POST","/drivers/${this.id}/billing-events",data=evnt)

        return BillingEvent()
    }

    /**
     * List all charges for this driver.
     *
     * @param eventType Filter by type. Defaults to null.
     * @param eventStatus Filter by status. Defaults to null.
     * @param maxRecords Maximum number of records to return. Defaults to null
     * @return
     */
    suspend fun listCharges(
        eventType: BillingEventType? = null,
        eventStatus: BillingEventStatus? = null,
        maxRecords: Int? = null
    ): Flow<BillingEvent> = flow {
        checkId()
        checkApiHandler(this@Driver.api)

        val params:MutableMap<String, String?> = mutableMapOf()

        if (eventType != null){
            params["type"] = eventType.toString()
        }

        if (eventStatus != null){
            params["status"] = eventStatus.toString()
        }

        this@Driver.api!!.batchFetch("drivers/${this@Driver.id}/billing-events",params).collect{res ->
            val billingEvent: BillingEvent = Json.decodeFromJsonElement(res)
            billingEvent.api = this@Driver.api
            emit(billingEvent)
        }
    }

    /**
     * Persist any changes in the API.
     *
     * @param fields The API formatted fields to update. If not supplied, any set fields in the model will be updated in the API. Defaults to null.
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        val f = fields ?: this.changedFields
        this.modelSave("/drivers/${this.id}", f)
    }

    /**
     * Update a field on the model, call save or keyword persist to persist changes in the API.
     *
     * @param persist  Whether to persist the changes to the API. Defaults to False.
     * @param fields  The model fields to update.
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        this.modelUpdate("/drivers/${this.id}", persist, fields)
    }

    /**
     * List trackable models for this driver.
    Depending on the org settings, this will return a model that contains a source ID.
    The source ID (not the ID) will be used to identify the model for telematics.
     *
     * @param fleetId The fleet ID to filter on. Defaults to null.
     * @return
     */
    suspend fun listTrackableModels(fleetId: String? = null): List<Any> {
        if (this.api?.orgData == null) {
            this.api?.refreshOrgData()
        }

        val sidType = this.api?.orgData?.sourceIdType

        val assets: MutableList<Any> = mutableListOf()

        if (sidType == "drv") {
            val drvs = this.listVehicles()
            return drvs.filter { it.isActive == true }
        } else if (sidType == "rv") {
            val drvs = this.listVehicles()
            return drvs.filter { it.isActive == true && it.registeredVehicle?.isActive == true }
        } else if (sidType == "d") {
            return listOf(this)

        } else if (sidType == "fd") {
            val fleets = this.listFleets()

            if (fleets != null) {
                for (fleet in fleets) {
                    if (fleetId != null) {
                        if (fleet.id != fleetId) {
                            continue
                        }
                    }
                    checkId()
                    fleet.listDriverVehicleAssignment(
                        this.id!!,
                        includeUnassigned = (sidType == "frv")
                    ).collect { fdrv ->
                        if (sidType == "fdrv") {
                            if (fdrv.assigned && fdrv.isActive) {
                                assets.add(fdrv)
                            }
                        }
                    }
                }
            }
        } else if (sidType == "fdrv" || sidType == "frv") {
            //frv and fdrvs will be returned here as 'open to all' frv's are returned as well
            val fleets = this.listFleets()

            if (fleets != null) {
                for (fleet in fleets) {
                    if (fleetId != null) {
                        if (fleetId != fleet.id) {
                            continue
                        }
                        checkId()
                        fleet.listDriverVehicleAssignment(this.id!!, (sidType == "frv"))
                            .collect { fdrv ->
                                if (sidType == "fdrv") {
                                    // only return fdrvs that are assigned to the driver and active
                                    // TODO  FIX /check this is right
//                                    if (fdrv.assigned && fdrv.isActive) {
//                                        assets.add(fdrv.driver)
//                                    }
                                }
                            }
                    }
                }
            }
        }
        return assets
    }

    /**
     *  Get the tracking ID for this driver.
     *
     * @return
     */
    suspend fun trackingId(): String? {

        val assets = this.listTrackableModels()

        if (assets.isEmpty()) {
            return null
        }
//        assets[0].javaClass.getField("sourceId")
        return readPropertyOfAny(assets[0], "sourceId")
    }
}

/**
 * Occupation
 *
 * @property position
 * @property employerName
 * @property adrLine1
 * @property adrLine2
 * @property adrLine3
 * @property county
 * @property province
 * @property postcode
 * @property lat
 * @property lng
 * @property officeDaysWeek
 * @property officeHours
 * @constructor Create empty Occupation
 */
@Serializable
class Occupation(
    @SerialName("position") var position: String? = null,
    @SerialName("employerName") var employerName: String? = null,
    @SerialName("adrLine1") var adrLine1: String? = null,
    @SerialName("adrLine2") var adrLine2: String? = null,
    @SerialName("adrLine3") var adrLine3: String? = null,
    @SerialName("county") var county: String? = null,
    @SerialName("province") var province: String? = null,
    @SerialName("postcode") var postcode: String? = null,
    @SerialName("lat") var lat: Float? = null,
    @SerialName("lng") var lng: Float? = null,
    @SerialName("officeDaysWeek") var officeDaysWeek: Int? = null,
    @SerialName("officeHours") var officeHours: Int? = null
)
