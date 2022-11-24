package com.inaza.androidsdk.models.billing

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import com.inaza.androidsdk.helpers.checkApiHandler
import com.inaza.androidsdk.models.custom.PrivateApiHandler
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.OffsetDateTime

/**
 * Status
 *
 * @constructor Create empty Status
 */
enum class Status {
    /**
     * Pending
     *
     * @constructor Create empty Pending
     */
    pending,

    /**
     * Paid
     *
     * @constructor Create empty Paid
     */
    paid,

    /**
     * Failed
     *
     * @constructor Create empty Failed
     */
    failed,

    /**
     * Cancelled
     *
     * @constructor Create empty Cancelled
     */
    cancelled,

    /**
     * Confirmed
     *
     * @constructor Create empty Confirmed
     */
    confirmed,
}

/**
 * Billing event
 *
 * @property externalID
 * @property paymentID
 * @property amount
 * @property message
 * @property paymentOut
 * @property paymentDate
 * @property status
 * @property approvalAt
 * @property policyID
 * @property type
 * @property id The billing event's unique ID
 * @property createdAt
 * @property billingAccount
 * @property approvalBy
 * @property api
 * @constructor Create empty Billing event
 */
@Serializable
data class BillingEvent(
    @SerialName("externalId")
    val externalID: String? = null,

    @SerialName("paymentId")
    val paymentID: String? = null,

    val amount: Int = 0,
    val message: String? = null,
    val paymentOut: Boolean = false,
    // TODO may be just a DATE
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val paymentDate: OffsetDateTime? = null,
    var status: BillingEventStatus? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val approvalAt: OffsetDateTime? = null,

    @SerialName("policyId")
    val policyID: String? = null,

    val type: BillingEventType? = null,
    val id: String? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime? = null,
    val billingAccount: BillingAccount? = null,
    val approvalBy: String? = null,
    @Transient
    override var api: ApiHandler? = null,
) : PrivateApiHandler() {
    /**
     * Get the ISO currency code for the billing event.
     *
     * @return
     */
    suspend fun getCurrency(): String? {
        if (this.billingAccount != null) {
            return this.billingAccount.currencyISOCode
        }
        return null
    }

    /**
     * Check if the billing event has been approved.
     *
     * @return
     */
    suspend fun isApproved(): Boolean {
        if (this.approvalAt != null) {
            return true
        }
        return false
    }

    /**
     * Update
     *
     * @param data
     */
    suspend fun update(data: MutableMap<String, Any>) {
        checkApiHandler(this.api)
        this.api!!.makeAuthRequest("PATCH", "/billing-events/${this.id}", data = data)
    }

    /**
     * Update the status of the billing event. Optionally add a payment ID.
     *
     * @param status
     * @param paymentId
     */
    suspend fun updateStatus(status: BillingEventStatus, paymentId: String? = null) {
        var data = mutableMapOf<String, Any>()

        data["status"] = status

        if (paymentId != null) {
            data["paymentId"] = paymentId
        }

        this.update(data)

        this.status = status

    }
}

@Serializable
enum class BillingEventStatus {
    pending,
    paid,
    failed,
    cancelled,
    confirmed
}

@Serializable
enum class BillingEventType {
    other,
    rates,
    base_premium
}