package com.inaza.androidsdk.sensor


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import kotlin.system.exitProcess

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    val interval: Double,
    sensorType: Int
) : MeasurableSensor(sensorType), SensorEventListener {

    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun startListening() {
        if (!doesSensorExist) {
            return
        }

        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            //1000000 microseconds is 1 second
            sensorManager.registerListener(this, it, SENSOR_DELAY_NORMAL)
        }
    }

    override fun stopListening() {
        if (!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (!doesSensorExist) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            invokeListener(event.values.toList())
//            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit


    override fun invokeListener(event: List<Float>){
        onSensorValuesChanged?.invoke(event)
    }

}
