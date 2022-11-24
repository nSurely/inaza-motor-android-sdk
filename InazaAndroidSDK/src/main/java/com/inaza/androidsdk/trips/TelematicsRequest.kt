package com.inaza.androidsdk.trips

import com.inaza.androidsdk.location.LocationData
import com.inaza.androidsdk.sensor.AccelerometerValues
import com.inaza.androidsdk.sensor.GyroscopeValues
import kotlinx.serialization.Serializable

@Serializable
data class TelematicsRequest(
    val sourceId: String,
    val orgId: String,
    val gps: List<LocationData>,
    val gyro: List<GyroscopeValues>,
    val acc: List<AccelerometerValues>
)