package models.policy

import android.os.Build
import androidx.annotation.RequiresApi
import api.ApiHandler
import helpers.KOffsetDateTimeSerializer
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.policy.enums.PolicyCoverType
import models.policy.enums.PolicyGroup
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.properties.Delegates
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Policy
 *
 * @property api
 * @property approval The approval details for the policy
 * @property cancellation The cancellation details for the policy
 * @property config The configuration details for the policy
 * @property contribution The contribution details for the policy
 * @property driver The driver details for the policy
 * @property duration The duration details for the policy
 * @property excess The excess details for the policy
 * @property extras The extras details for the policy
 * @property fees The fees details for the policy
 * @property final The final details for the policy
 * @property issuer The issuer details for the policy
 * @property noClaims The no claims details for the policy
 * @property policyCertificateLoc
 * @property premium The premium details for the policy
 * @property rates The rates details for the policy
 * @property rewards The rewards details for the policy
 * @property telematics The rewards details for the policy
 * @property canRenew If the policy can be renewed. If not the policy must be cancelled and a new record with a new policy ID created.
 * @property cover The cover types for this policy
 * @property createdAt The date and time this policy record was created
 * @property policyGroup The policy group this policy belongs to
 * @property id The unique identifier for this policy
 * @property isActivePolicy If the policy is the active to be used for pricing.
This field is useful when constructing a multi-use policy, such as a vehicle with an owner who is traditional insurance, and a named driver on UBI.
> Note: The end-user still may see this record. This field is just used for pricing.
 * @property maxPassengers The maximum number of passengers that can be covered by this policy. This may not apply to all cover types."
 * @property sumInsured The maximum sum insured on this policy.
 * @constructor Create empty Policy
 */
@kotlinx.serialization.Serializable
class Policy @RequiresApi(Build.VERSION_CODES.O) constructor(
    @Transient
    override var api: ApiHandler? = null,

    @SerialName("approval")
    private val _approval: PolicyApproval = PolicyApproval(),

    @SerialName("cancellation")
    private val _cancellation: PolicyCancellation = PolicyCancellation(),

    @SerialName("config")
    private val _config: PolicyConfig = PolicyConfig(),

    @SerialName("contribution")
    private val _contribution: PolicyContribution = PolicyContribution(),

    @SerialName("driver")
    private val _driver: PolicyDriver = PolicyDriver(),

    @SerialName("duration")
    private val _duration: PolicyDuration = PolicyDuration(OffsetDateTime.now(ZoneOffset.UTC)),

    @SerialName("excess")
    private val _excess: PolicyExcess = PolicyExcess(),

    @SerialName("extras")
    private val _extras: PolicyExtrasNested = PolicyExtrasNested(),

    @SerialName("fees")
    private val _fees: PolicyFees = PolicyFees(),

    @SerialName("final")
    private val _final: PolicyFinalPricing = PolicyFinalPricing(),

    @SerialName("issuer")
    private val _issuer: PolicyIssuer = PolicyIssuer(),

    @SerialName("noClaims")
    private val _noClaims: PolicyNoClaims = PolicyNoClaims(),

    @SerialName("policyCertificateLoc")
    private val _policyCertificateLoc: String? = null,

    @SerialName("premium")
    private val _premium: PolicyBasePremium = PolicyBasePremium(),

    @SerialName("rates")
    private val _rates: PolicyRates = PolicyRates(),

    @SerialName("rewards")
    private val _rewards: PolicyRewards = PolicyRewards(),

    // must constrain
    @SerialName("telematics")
    private val _telematics: PolicyTelematics = PolicyTelematics(),

    @SerialName("canRenew")
    private val _canRenew: Boolean = true,

    @SerialName("cover")
    private val _cover: List<PolicyCoverType> = listOf(),

    // Can't change created at time
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    @SerialName("createdAt")
    override val createdAt: OffsetDateTime? = null,

    @SerialName("policyGroup")
    private var _policyGroup: PolicyGroup? = null,

    @SerialName("id")
    private var _id: String? = null,

    @SerialName("isActivePolicy")
    private var _isActivePolicy: Boolean = true,

    @SerialName("maxPassengers")
    private var _maxPassengers: Int = 1,

    // must constrain
    @SerialName("sumInsured")
    private val _sumInsured: Double = 0.0
) : PolicyBase() {
    @Transient
    var changedFields = mutableMapOf<String, Any?>()


    override var maxPassengers: Int by Delegates.observable(_maxPassengers) { prop, old, new ->
        changedFields[prop.name] = new
    }

    override var sumInsured: Double by Delegates.observable(_sumInsured) { prop, old, new ->
        changedFields[prop.name] = new
    }
    override var isActivePolicy: Boolean by Delegates.observable(_isActivePolicy) { prop, old, new ->
        changedFields[prop.name] = new
    }
    override var id: String? by Delegates.observable(_id) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }

    override var cover: List<PolicyCoverType> by Delegates.observable(_cover) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    override var canRenew: Boolean by Delegates.observable(_canRenew) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var telematics: PolicyTelematics by Delegates.observable(_telematics) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    override var policyGroup: PolicyGroup? by Delegates.observable(_policyGroup) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }

    var rewards: PolicyRewards by Delegates.observable(_rewards) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var rates: PolicyRates by Delegates.observable(_rates) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var premium: PolicyBasePremium? by Delegates.observable(_premium) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var policyCertificateLoc: String? by Delegates.observable(_policyCertificateLoc) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var noClaims: PolicyNoClaims? by Delegates.observable(_noClaims) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var final: PolicyFinalPricing by Delegates.observable(_final) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var fees: PolicyFees? by Delegates.observable(_fees) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var extras: PolicyExtrasNested? by Delegates.observable(_extras) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var excess: PolicyExcess? by Delegates.observable(_excess) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var duration: PolicyDuration? by Delegates.observable(_duration) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var driver: PolicyDriver by Delegates.observable(_driver) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var contribution: PolicyContribution? by Delegates.observable(_contribution) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var config: PolicyConfig? by Delegates.observable(_config) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var cancellation: PolicyCancellation by Delegates.observable(_cancellation) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }
    var approval: PolicyApproval by Delegates.observable(_approval) { prop, old, new ->
        if (new != null) {
            changedFields[prop.name] = new
        }
    }


    /**
     * Is the policy cancelled?
     *
     * @return
     */
    suspend fun isCancelled(): Boolean {

        return this.cancellation.isCancelled()
    }

    /**
     * Is the policy approved?
     *
     * @return
     */
    suspend fun isApproved(): Boolean {
        return this.approval.isApproved()
    }

    /**
     * Is the policy expired?
     *
     * @return
     */
    suspend fun isExpired(): Boolean {
//        return this.duration.isExpired()
        return true
    }


    /**
     * Has the driver agreed to the policy?
     *
     * @return
     */
    suspend fun isDriverAgreed(): Boolean {
        return this.driver.isAgreed()
    }

    /**
     * Is the policy live? ie. active, approved, driver agreed, not cancelled and not expired.
     *
     * @return
     */
    suspend fun isLive(): Boolean {
        // TODO ask @ self.is_active???
        if (this.isApproved() && !this.isCancelled() && !this.isExpired() && this.isDriverAgreed()) {
            return true
        }
        return false
    }

    /**
     * Has the driver agreed to the policy?
     *
     * @return
     */
    suspend fun ratePerKm(): Float? {
        // TODO check these names are correct
        if (!this.rates.enabled) {
            return null
        }

        return this.final.rates.value
    }

    /**
     * The rate per mile for the policy.
     *
     * @return
     */
    suspend fun ratePerMile(): Float? {
        if (!this.rates.enabled) {
            return null
        }

        return this.final.rates.value * 1.60934.toFloat()


    }

    /**
     * Premium amount
     *
     * @return
     */
    suspend fun premiumAmount(): Int {
        // TODO check these names are correct
        return this.final.premium.value
    }

    private suspend fun checkId() {
        if (this.id == null) {
            throw Exception("ID must be set")
        }
    }

    /**
     * Approve the the policy on behalf of the driver.
     *
     * @param refresh Refresh the policy after approval.
     */
    suspend fun driverApprove(refresh: Boolean = true) {
        this.checkId()

        val body = mutableMapOf<String, Any>(
            "driver" to mutableMapOf<String, Any>(
                "agreedAt" to OffsetDateTime.now(ZoneOffset.UTC)
            )
        )
    }

    /**
     * Approve the the policy internally.
     *
     * @param refresh Refresh the policy after approval.
     * @param approvedById
     */
    suspend fun internalApprove(refresh: Boolean = true, approvedById: String? = null) {

    }

    /**
     * Cancel the policy.
     *
     * @param refresh  Refresh the policy after cancellation.
     * @param message
     */
    suspend fun cancel(refresh: Boolean = true, message: String? = null) {
        this.checkId()
    }

    /**
     * Refresh the model from the API.
     *
     */
    suspend fun refresh() {
        this.checkId()

        val refresh: Policy = Json.decodeFromString(
            this.api!!.makeAuthRequest("GET", "/policy/${this.id}").readText()
        )

        for (p in Policy::class.memberProperties) {
            val prop = p.name

            val value = Policy::class.memberProperties
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
        this.api!!.makeAuthRequest("DELETE", "/policy/${this.id}")

    }

    /**
     * Persist any changes in the API.
     *
     * @param fields The API formatted fields to update. If not supplied, any set fields in the model will be updated in the API. Defaults to null.
     */
    suspend fun save(fields: MutableMap<String, Any>? = null) {
        this.checkId()
        val f = fields ?: this.changedFields
        this.modelSave("/policy/D-HJKVM71659515417", f)
    }

    /**
     * Update a field on the model, call save or keyword persist to persist changes in the API.
    When updating nested fields, you must call update on the nested object.
    Call save here when done updating a nested object.
     *
     * @param persist Whether to persist the changes to the API. Defaults to false.
     * @param fields The model fields to update.
     */
    suspend fun update(persist: Boolean = false, fields: MutableMap<String, Any?>) {
        this.modelUpdate("/policy/${this.id}", persist, fields)
    }


    /**
     * Create a policy.
    Note: it is best to create a policy on the desired object (eg. vehicle, driver)
     * - d (driver): record ID is the driver ID
     * - rv (vehicle): record ID is the vehicle ID
     * - drv (driver): record ID is the driver vehicle ID
     * - fd (fleet driver): record ID is the fleet ID, and driver_id is the driver ID
     * - frv (fleet vehicle): record ID is the fleet ID, and vehicle_id is the vehicle ID
     * - fdrv (fleet driver vehicle): record ID is the fleet ID, and driver_id and vehicle_id are the driver and vehicle IDs
     * @param apiHandler api handler
     * @param recordId recordId, to place policy on
     * @param driverId Driver id, required for some fleet policies. Defaults to None.
     * @param vehicleId Vehicle id, required for some fleet policies. Defaults to None.
     * @return
     */
    suspend fun create(
        apiHandler: ApiHandler,
        recordId: String,
        driverId: String? = null,
        vehicleId: String? = null
    ): Policy {
        //TODO say to Niall, should policy group id be an arg here?
        var recordId = recordId
        var params = mutableMapOf<String, String?>()
        if (this.policyGroup == PolicyGroup.d) {
            if (driverId != null) {
                if (driverId != recordId) {
                    recordId = driverId
                }
            }
        } else if (this.policyGroup == PolicyGroup.drv) {

        } else if (this.policyGroup == PolicyGroup.fd) {
            if (driverId == null) {
                throw java.lang.Exception("driverId must be supplied for FD policies")
            } else if (driverId == recordId) {
                throw java.lang.Exception("recordId (fleet id) must not be the same as driver_id for FD policies")
            }

            params["driverId"] = driverId
        } else if (this.policyGroup == PolicyGroup.fdrv) {
            if (driverId == null) {
                throw java.lang.Exception("driverId must be supplied for FDRV policies")
            }
            if (vehicleId == null) {
                throw java.lang.Exception("vehicleId must be supplied for FDRV policies")
            }

            params["vehicleId"] = vehicleId
            params["driverId"] = driverId
        } else if (this.policyGroup == PolicyGroup.frv) {
            if (vehicleId == null) {
                throw java.lang.Exception("vehicleID must be supplied for FDRV policies")
            } else if (vehicleId == recordId) {
                throw java.lang.Exception("recordId (fleet id) must not be the same as vehicle_id for FDRV policies")
            }

            params["vehicleId"] = vehicleId
        }
        val json = Json.encodeToString(this)
        val res = apiHandler.makeAuthRequest("POST", "/policy/", params, json, null)
        val policy: Policy = Json.decodeFromString(res.readText())


        // Implement a refresh here with the new policy
        return policy
    }

}


