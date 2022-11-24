package com.inaza.androidsdk.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import kotlinx.serialization.Serializable
import java.util.*

class LightSensor(
    context: Context,
    interval: Double
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT,
    interval = interval
)

class Accelerometer(
    context: Context,
    interval: Double
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
    interval = interval * 1000
) {
    var last: Double = 0.0
    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) {
            return
        }
        if (event?.sensor?.type == sensorType) {
//            if (this.last == 0.0) {
//                onSensorValuesChanged?.invoke(event.values.toList())
//                last = Calendar.getInstance().timeInMillis.toDouble()
//            } else {
//                if (Calendar.getInstance().timeInMillis - last > interval){
//                    onSensorValuesChanged?.invoke(event.values.toList())
//                    last = Calendar.getInstance().timeInMillis.toDouble()
//
//                }
//            }
            if (event.sensor?.type == sensorType) {
                if (Calendar.getInstance().timeInMillis - last >= interval) {
                    onSensorValuesChanged?.invoke(event.values.toList())
                    last = Calendar.getInstance().timeInMillis.toDouble()
                }
            }
        }
    }
}

@Serializable
data class AccelerometerValues(val x: Float, val y: Float, val z: Float, val ts: Long)


class Gyroscope(
    context: Context,
    interval: Double
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE,
    interval = interval * 1000
) {

    var last: Double = 0.0
    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            if (Calendar.getInstance().timeInMillis - last >= interval) {
//                onSensorValuesChanged?.invoke(event.values.toList())
                invokeListener(event.values.toList())
                last = Calendar.getInstance().timeInMillis.toDouble()
            }
        }
    }
}

@Serializable
data class GyroscopeValues(val x: Float, val y: Float, val z: Float, val ts: Long)