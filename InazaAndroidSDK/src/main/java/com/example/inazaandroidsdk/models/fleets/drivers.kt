package models.fleets

import api.ApiHandler
import com.example.inazaandroidsdk.helpers.checkApiHandler
import helpers.KOffsetDateTimeSerializer
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.custom.PrivateApiHandler
import models.drivers.Driver
import java.time.OffsetDateTime

/**
 * Represents a driver who is assigned to a fleet
 *
 * @property api
 * @property isVehicleManager
 * @property isDriverManager
 * @property isBillingManager
 * @property isActive
 * @property expiresAt
 * @property createdAt
 * @property sourceID
 * @property id
 * @property driver
 * @constructor Create empty Fleet driver
 */
@Serializable
data class FleetDriver(
    @Transient
    override var api: ApiHandler? = null,
    val isVehicleManager: Boolean = false,
    val isDriverManager: Boolean = false,
    val isBillingManager: Boolean = false,
    val isActive: Boolean = false,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val expiresAt: OffsetDateTime? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime? = null,

    @SerialName("sourceId")
    val sourceID: String? = null,
    val id: String? = null,
    val driver: Driver
) : PrivateApiHandler() {
    suspend fun telematicsId(): String? {
        return this.sourceID
    }

    /**
     * Return the fleet.
     *
     */
    suspend fun getFleet(): Fleet? {
        checkApiHandler(this.api)
        val response: HttpResponse = this.api!!.makeAuthRequest("GET", "/fleets/${this.id}")
        return Json.decodeFromString(response.readText())

    }
}
