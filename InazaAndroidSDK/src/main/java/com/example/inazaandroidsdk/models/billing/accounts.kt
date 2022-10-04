package models.billing

import api.ApiHandler
import helpers.KOffsetDateTimeSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import models.custom.PrivateApiHandler
import java.time.OffsetDateTime

/**
 * Billing account
 *
 * @property api ApiHandler object used to communicate with Inaza API
 * @property adrLine1
 * @property adrLine2
 * @property adrLine3
 * @property county
 * @property province
 * @property postcode
 * @property externalID
 * @property adrSameAsHome
 * @property expiry
 * @property updatedAt
 * @property isActive
 * @property isPrimary
 * @property thirdPartyID The third party ID for the billing account, eg. Stripe account ID.
 * @property card The card details for this billing account. Card number, cvv and expiry are deleted after 3 hours.
 * @property id The billing account's unique ID.
 * @property createdAt
 * @property countryISOCode
 * @property currencyISOCode
 * @constructor Create empty Billing account
 */
@Serializable
class BillingAccount(
    @Transient
    override var api: ApiHandler? = null,
    val adrLine1: String? = null,
    val adrLine2: String? = null,
    val adrLine3: String? = null,
    val county: String? = null,
    val province: String? = null,
    val postcode: String? = null,

    @SerialName("externalId")
    val externalID: String? = null,
    // Missing from motorpy
    val adrSameAsHome: Boolean? = null,
    val expiry: LocalDate? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val updatedAt: OffsetDateTime? = null,
    val isActive: Boolean = false,
    val isPrimary: Boolean = false,

    @SerialName("thirdPartyId")
    val thirdPartyID: String? = null,

    val card: Card? = null,
    val id: String? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime? = null,

    @SerialName("countryIsoCode")
    val countryISOCode: String? = null,

    @SerialName("currencyIsoCode")
    val currencyISOCode: String

    // Driver not in motorpy due to circular imports
    // May or may not need to add here
) : PrivateApiHandler() {
    /**
     * A simple display string to identify the model to the user.
     *
     * @return
     */
    suspend fun getDisplay(): String {
        if (this.card == null) {
            return "Unknown"
        }
        var display: String = ""
        if (this.isPrimary) {
            display += "Primary"
        }

        display += "%s - %s".format(this.card.name, this.card.lastFour)
        return display
    }
}

/**
 * Card
 *
 * @property name The name of the cardholder.
> This data is retained.
 * @property lastFour The last four digits of the card number. This is set from the card number when the card is created.
> This data is retained.
 * @property number The card number, as a string of digits.
> This data is deleted after 3 hours.
 * @property exp Expiration date of the card.
> This data is deleted after 3 hours.
 * @property updatedAt The date and time the card was added/updated.
> This data is retained.
 * @property thirdPartyID The third party identifier for the card.
> This data is retained.
 * @property cvv The CVV code, as a string of digits.
> This data is deleted after 3 hours.
 * @constructor Create empty Card
 */
@Serializable
class Card(
    val name: String?,
    val lastFour: String? = null,
    val number: String?,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val exp: OffsetDateTime,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val updatedAt: OffsetDateTime? = null,
    val cvv: String?,
    @SerialName("thirdPartyId")
    val thirdPartyID: String? = null


) {
    init {
        // Add constraints here
    }
}

