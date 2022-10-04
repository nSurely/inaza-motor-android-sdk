package models.billing

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.OffsetDateTime
import java.time.ZoneOffset


class BillingModelTest {
    @Test
    fun testModelInit() {
        val account = BillingAccount(
            id = "123",
            externalID = "123",
            expiry = LocalDate(2022, 10, 1),
            updatedAt = OffsetDateTime.of(2022, 9, 3, 2, 5, 6, 6, ZoneOffset.UTC),
            isActive = true,
            isPrimary = true,
            createdAt = OffsetDateTime.of(2022, 4, 2, 12, 5, 6, 6, ZoneOffset.UTC),
            currencyISOCode = "USD",
            countryISOCode = "US",
            adrLine1 = "123",
            adrLine2 = "123",
            adrLine3 = "123",
            county = "123",
            province = "12",
            postcode = "123",
            // missing from motor py
            adrSameAsHome = true
        )
    }

    @Test
    fun testJsonInit() {
        LocalDateTime
        val json = """{
            "id": "123",
            "externalId": "123",
            "expiry": "2022-01-01",
            "updatedAt": "2022-10-15T09:27:37",
            "isActive": true,
            "isPrimary": true,
            "createdAt": "2022-02-05T21:23:32",
            "currencyIsoCode": "USD",
            "countryIsoCode": "US",
            "adrLine1": "123",
            "adrLine2": "123",
            "adrLine3": "123",
            "county": "123",
            "province": "12",
            "postcode": "123",
            "adrSameAsHome": true
        }"""
        val account: BillingAccount = Json.decodeFromString<BillingAccount>(json)
    }

    @Test
    fun testInvalidDatesJson() {
        assertThrows<IllegalArgumentException> {
            val json = """{
            "id": "123",
            "externalId": "123",
            "expiry": "Should be date",
            "updatedAt": "Should be datetime",
            "isActive": true,
            "isPrimary": true,
            "createdAt": "Should be date time",
            "currencyIsoCode": "USD",
            "countryIsoCode": "US",
            "adrLine1": "123",
            "adrLine2": "123",
            "adrLine3": "123",
            "county": "123",
            "province": "12",
            "postcode": "123",
            "adrSameAsHome": true
        }"""
            val account: BillingAccount = Json.decodeFromString<BillingAccount>(json)
        }
    }
}