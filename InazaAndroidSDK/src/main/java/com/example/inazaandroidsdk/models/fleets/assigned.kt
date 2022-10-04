package models.fleets

import api.ApiHandler
import helpers.KOffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import models.custom.PrivateApiHandler
import models.drivers.Driver
import com.example.inazaandroidsdk.models.vehicles.Vehicle
import java.time.OffsetDateTime

/**
 * FDRV Assignment. This represents a vehicle that a driver is assigned to.
 *
 * @property api Api Handler object uses to connect to the Inaza API.
 * @property expiresAt
 * @property createdAt
 * @property isActive
 * @property assigned
 * @property sourceId
 * @property registeredVehicle
 * @property driver
 * @constructor Create empty Fleet driver vehicle assignment
 */
@Serializable
class FleetDriverVehicleAssignment(
    @kotlinx.serialization.Transient
    override var api: ApiHandler? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val expiresAt: OffsetDateTime? = null,
    @Serializable(with = KOffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime? = null,
    val isActive: Boolean = false,
    val assigned: Boolean = false,
    val sourceId: String? = null,
    val registeredVehicle: Vehicle? = null,
    val driver: Driver? = null,
) : PrivateApiHandler() {
    /**
     * Returns the driver and vehicle ID if the vehicle is set
     *
     * @return
     */
    suspend fun id(): Pair<String?, String?> {
        return Pair(this.driver?.id, this.registeredVehicle?.id)
    }

    /**
     * Return the telematics ID.
     *
     * @return
     */
    suspend fun telematicsId(): String? {
        return this.sourceId
    }
}