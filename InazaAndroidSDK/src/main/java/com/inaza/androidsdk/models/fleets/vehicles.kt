package com.inaza.androidsdk.models.fleets

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import com.inaza.androidsdk.models.custom.PrivateApiHandler
import com.inaza.androidsdk.models.vehicles.Vehicle
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.OffsetDateTime

/**
 * Represents a vehicle that is assigned to a fleet
 *
 * @property api
 * @property sourceId
 * @property expiresAt
 * @property createdAt
 * @property registeredVehicle
 * @constructor Create empty Fleet vehicle
 */
@Serializable
class FleetVehicle(
    @Transient
    override var api: ApiHandler? = null,
    val sourceId: String? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val expiresAt: OffsetDateTime? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime? = null,
    val registeredVehicle: Vehicle? = null
) : PrivateApiHandler() {
    /**
     * Returns the vehicle ID if the vehicle is set
     *
     * @return
     */
    suspend fun id(): String? {
        if (this.registeredVehicle != null) {
            return this.registeredVehicle.id
        }
        return null
    }
}

