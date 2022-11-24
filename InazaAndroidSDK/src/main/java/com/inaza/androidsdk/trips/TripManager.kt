package com.inaza.androidsdk.trips

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.inaza.androidsdk.activity.ActivityTransitionService
import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.api.OrgSettings
import com.inaza.androidsdk.helpers.StorageManager
import com.inaza.androidsdk.helpers.getDistanceFromLatLonInKm
import com.inaza.androidsdk.location.LocationData
import com.inaza.androidsdk.location.LocationService
import com.inaza.androidsdk.location.TripStatistics
import com.inaza.androidsdk.location.TripStatus
import com.inaza.androidsdk.sensor.*
import com.inaza.androidsdk.sensor.AccelerometerValues
import com.inaza.androidsdk.sensor.GyroscopeValues
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.system.exitProcess

//// Why pass in the APIHandler when it  inherits one anyway?
//// No region or Url passed?
//// orgId null by default?
open class TripManager(
    val sourceId: String,
    val batchWindow: Float = 20.0f,
    val context: Context,
    val api: ApiHandler,
    val testDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BroadcastReceiver() {
    var lastBatchTime = (System.currentTimeMillis() / 1000)
    var tripActive = false
    var activityType = "Null"//Activity.fromInt(7)

    var orgSettings: OrgSettings

    // Might need to use application context here?
    protected var storageManager: StorageManager = StorageManager(context)
    lateinit var gyroscope: MeasurableSensor

    // API handler is inherited so doesn't need to be passed in?
    init {
        this.storageManager.clear()

        runBlocking {
            orgSettings = this@TripManager.getOrgPublicSettings()
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            orgSettings = this@TripManager.getOrgPublicSettings()
//        }

//        val channel = NotificationChannel(
//            "trip",
//            "Trip",
//            NotificationManager.IMPORTANCE_LOW
//        )
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)

        if (this.batchWindow < 0) {
            println("raise error")
        }
        this.storageManager.putInt("Activity", 4)
        this.storageManager.putListObject(
            "tripDetection", arrayListOf()
        )

        if (this.batchWindow > 60) {
            println("raise error")
        }
    }

    private suspend fun clear() {
        this.setLocationArray(arrayListOf())
        this.setAccData(arrayListOf())
        this.setGyroscopeData(arrayListOf())
    }

    private suspend fun sendCheck() {
//        if (this.gps.isEmpty() && this.accelerometer.isEmpty() && this.gyroscope.isEmpty() && this.alerts.isEmpty()){
//            return
//        }
//
//        if (this.batchWindow  != 0.0f){
//            if(((System.currentTimeMillis() / 1000) - this.lastBatchTime) < this.batchWindow) return
//        }
//
//        val body = mutableMapOf<String,Any>()

//        body["sourceID"] = this.sourceId
////        body["orgId"] = this.orgId
//        body["gps"] = this.gps
//        body["acc"] = this.accelerometer
//        body["gyro"] = this.gyroscope
//        body["alerts"] = this.alerts

//        this.apiHandler.telematicsRequest("POST", "/track", data=body)
        this.clear()
    }

    suspend fun addGps(
        lat: Float?,
        lng: Float?,
        gpsAccuracy: Float?,
        altitude: Float?,
        acceleration: Float?,
        speed: Float?,
        bearing: Float?,
        bearingAccuracy: Float?,
        verticalAcceleration: Float?,
        timestamp: Long?
    ) {
        if (lat == null || lng == null) {
            // throw
        }

        if (lat != null) {
            if (lat < -90 || lat > 90) {
                // throw
            }
        }

        if (lng != null) {
            if (lng < -180 || lng > 180) {
                //throw
            }
        }

        if (timestamp == null) {
            var timestamp = System.currentTimeMillis() / 1000
        }

        // Add timestamp
        val data = mapOf<String, Any?>(
            "lat" to lat, "lng" to lng, "a" to gpsAccuracy, "alt" to altitude,
            "acc" to acceleration, "s" to speed, "b" to bearing, "bAcc" to bearingAccuracy,
            "va" to verticalAcceleration, "ts" to timestamp
        )

//        this.gps.add(data)
    }

    suspend fun addAccelerometer(x: Float?, y: Float?, z: Float?, timestamp: Long?) {

        if (x == null || y == null || z == null) {
            // throw
        }

        if (timestamp == null) {
            var timestamp = System.currentTimeMillis() / 1000
        }

        val data = mapOf<String, Any?>("x" to x, "y" to y, "z" to z, "ts" to timestamp)
//        this.accelerometer.add(data)

        this.sendCheck()
    }


    suspend fun addGyroscope(x: Float?, y: Float?, z: Float?, timestamp: Long?) {

        if (x == null || y == null || z == null) {
            // throw
        }

        if (timestamp == null) {
            var timestamp = System.currentTimeMillis() / 1000
        }

        val data = mapOf<String, Any?>("x" to x, "y" to y, "z" to z, "ts" to timestamp)
//        this.gyroscope.add(data)

        this.sendCheck()
    }

    suspend fun addAlert(
        alertCode: String?,
        measurement1: Float?,
        measurement2: Float?,
        measurement3: Float?,
        onDevice: Boolean,
        shown: Boolean,
        timestamp: Long?
    ) {
        // throw

        if (alertCode == null) {
            //throw
        }
        if (timestamp == null) {
            var timestamp = System.currentTimeMillis() / 1000
        }

        val data = mapOf<String, Any?>(
            "code" to alertCode, "m1" to measurement1,
            "m2" to measurement2, "m3" to measurement3, "onDevice" to onDevice, "shown" to shown,
            "ts" to timestamp
        )

//    this.alerts.add(data)

        this.sendCheck()
    }

//    suspend fun updateLocationData(location: Location) {
//
//        // Use an object here.
//        val data = storageManager.getListObject("locationData", LocationData::class.java)
//        val loc = LocationData(
//            System.currentTimeMillis() / 1000,
//            location.latitude,
//            location.longitude,
//            location.accuracy,
//            this.activityType,
//            location.altitude,
//            location.verticalAccuracyMeters,
//            location.speed,
//            location.speedAccuracyMetersPerSecond,
//            location.accuracy,
//            location.bearing,
//            location.bearingAccuracyDegrees,
//            null
//        )
//        data.add(loc)
//        storageManager.putListObject("locationData", data)
//    }

    suspend fun setTripStatistics(stats: TripStatistics) {
        storageManager.putObject("tripStatistics", stats)
    }

    suspend fun clearCache(){
        storageManager.clear()
    }

    // Redundant?
    suspend fun updateTripStatistics() {
        var stats = getTripStatistics()

        stats.distanceKm++
        storageManager.putObject("tripStatistics", stats)
    }

    suspend fun getTripStatistics(): TripStatistics {
        return storageManager.getObject("tripStatistics", TripStatistics::class.java)
    }

    suspend fun setLastTripStatistics(lastTripStatistics: TripStatistics) {
        storageManager.putObject("lastTripStatistics", lastTripStatistics)
    }

    suspend fun getLastTripStatistics(): TripStatistics {
        return storageManager.getObject("lastTripStatistics", TripStatistics::class.java)

    }

    //legacy?
    suspend fun generateTripId(): String {
        return "tid_" + Clock.System.now()
    }

    suspend fun setNotification(title: String, text: String, code: Int = 2) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "location")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setOngoing(true)
            .setPriority(-1)
        notificationManager.notify(code, notification.build())
    }

    // initiated by should default to whatever the manual one is
    suspend fun startTrip(
        initiatedBy: String = "manual",
        preLocations: ArrayList<LocationData> = arrayListOf()
    ) {
        Log.e("Trip", "Started!")

        if (initiatedBy.lowercase() != "auto") {
            // Start location tracking here

            // And also gyro and acc tracking?

            // Should these be collecting before a trip starts? For auto tracking?
            // TODO use defaults if org settings are missing for some reason
            val gyroInterval = this.orgSettings.telematics!!.dataCapture.gyroscopeInterval
            val accInterval = this.orgSettings.telematics!!.dataCapture.accelerometerInterval


            if (this.orgSettings.telematics?.dataCapture?.gyroscopeOn == true) {
                if (gyroInterval != null) {
                    this.subscribeToGyroscope(gyroInterval)
                }
            }
            if (this.orgSettings.telematics?.dataCapture?.accelerometerOn == true) {
                if (accInterval != null) {
                    this.subscribeToAccelerometer(accInterval)
                }
            }
            this.tripActive = true
            val intent = Intent(
                context,
                ActivityTransitionService::class.java
            )
            ContextCompat.startForegroundService(context, intent)


            // !! changed application context to context here for both
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                context.startService(this)
            }
            // Register the broadcast receiver for receiving location data
            val filter = IntentFilter("Location")
            this.context.registerReceiver(this, filter)
        }
        // Pre locations are the handful of valid coords collected before trip was triggered?

        // Causes Null Pointer Exception
//        if (this.getTripStatus().active) {
//            Log.e("E","Trip not started")
//            return
//        }

        // Clear Trip Statistics
//        this.setTripStatistics(TripStatistics(
//        ))

        // Clear polyline map array

        // Set trip status

//        this.setTripStatus(TripStatus(true, "auto"))

        this.tripActive = true
        // With Dispatchers IO
        this.startUploadService()
        // Begin tracking accelerometer and gyro here?

        // If prelocations present, add them in to the location data

        if (preLocations.isNotEmpty()) {
            // Logic looks wrong?
        }
    }


    suspend fun getSourceId() {

    }

    suspend fun endTrip(endedBy: String) {
        Intent(context.applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            context.applicationContext.startService(this)
        }
//        val displayName = this.storageManager.getString("orgSettings")
        val displayName = "DevOrg"
        this.setNotification("Trip Ended", "This trip was insured by $displayName")
        this.tripActive = false
        // fix, is float should be TripStatistics
//        val tripStatistics = this.getTripStatistics()

//        this.setLastTripStatistics(tripStatistics)

        // clear trip detection location array

        // change trip status


        // call back shit, not applicable?

        // Any other clean up
    }


    // upload service? Interval of some sort, call the api
    // Sending interval is 30 seconds

    suspend fun uploadTripData() {
        // Get Acc
        withContext(Dispatchers.IO) {
            val acc = this@TripManager.getAccData()
            // Get gyro
            val gyro = this@TripManager.getGyroscopeData()
            // Get location
            val locations = this@TripManager.getLocationArray()
            //Clear all, Implement a lock of sorts? Not too hard

            this@TripManager.clear()
            // Created request
            val body = TelematicsRequest(
                "drv-6112396133ac680001b72dd2",
                "dev-env-eu-west1",
                locations,
                gyro,
                acc
            )

            Log.e("Trip", body.toString())
            // Send to trip api
            try {
//        Log.e(
//            "POST", this.api.makeRequest(
//                "POST",
//                urlOverride = "https://dev-eu-1.nsurely-motor.com/v1/telematics/track/",
//                data = body,
//                params = null,
//                headers = null,
//                endpoint = ""
//            ).status.toString()
//        )
            } catch (e: Exception) {
                Log.e("f", e.toString())
            }
            Log.e("TripSMize", body.acc.size.toString())
        }
    }

    suspend fun fetchDefaultVehicle() {

        // Make an API request
        // check driver registered vehicles
        // see which is default.

    }

    suspend fun timeInMillis(): Long {
        val rightNow = Calendar.getInstance()

        val offset = (rightNow[Calendar.ZONE_OFFSET] +
                rightNow[Calendar.DST_OFFSET]).toLong()

        return (rightNow.timeInMillis + offset) %
                (24 * 60 * 60 * 1000)
    }

    suspend fun setDataLastSentTime(sentTime: Float) {
        // Convert instant to Float?
        storageManager.putFloat("lastDataSendTime", sentTime)
    }

    suspend fun getDataLastSentTime(): Float {
        return storageManager.getFloat("lastDataSendTime")
    }


    private suspend fun getTripStatus(): TripStatus {
        return storageManager.getObject("tripStatus", TripStatus::class.java)
    }

    suspend fun setTripStatus(status: TripStatus) {
        storageManager.putObject("tripStatus", status)
    }

    suspend fun getTripDetectionLocationArray(): ArrayList<LocationData> {
        return storageManager.getListObject(
            "tripDetection",
            LocationData::class.java
        ) as ArrayList<LocationData>

    }

    suspend fun setTripDetectionLocationArray() {
        storageManager.putListObject(
            "tripDetection",
            ArrayList<Any>()
        )
    }

    suspend fun updateTripDetectionLocationArray(location: LocationData) {
        val data = getTripDetectionLocationArray() as ArrayList<Any>

        data.add(location)

        storageManager.putListObject(
            "tripDetection",
            data
        )
    }

    suspend fun getLocationArray(): ArrayList<LocationData> {
        return storageManager.getListObject(
            "loc",
            LocationData::class.java
        ) as ArrayList<LocationData>
    }

    suspend fun setLocationArray(location: ArrayList<LocationData>) {
        storageManager.putListObject("loc", location as ArrayList<Any>)
    }

    suspend fun updateLocationArray(location: LocationData) {
        val data = storageManager.getListObject("loc", LocationData::class.java)
        data.add(location)

        storageManager.putListObject("loc", data)

    }

    suspend fun getPolylineData() {

    }

    suspend fun setPolylineData() {

    }

    suspend fun updatePolylineData() {}


    suspend fun getGyroscopeData(): ArrayList<GyroscopeValues> {
        return storageManager.getListObject(
            "gyroData",
            GyroscopeValues::class.java
        ) as ArrayList<GyroscopeValues>
    }

    suspend fun setGyroscopeData(gyroData: ArrayList<GyroscopeValues>) {
        storageManager.putListObject("gyroData", gyroData as ArrayList<Any>)
    }

    suspend fun updateGyroscopeData(gyroData: GyroscopeValues) {
        val data = getGyroscopeData()

        data.add(gyroData)

        this.setGyroscopeData(data)
    }

    suspend fun getAccData(): ArrayList<AccelerometerValues> {
        return storageManager.getListObject(
            "accData",
            AccelerometerValues::class.java
        ) as ArrayList<AccelerometerValues>
    }

    suspend fun setAccData(accData: ArrayList<Any>) {
        storageManager.putListObject("accData", accData)
    }

    suspend fun updateAccData(accData: AccelerometerValues) {
        val data = getAccData()

        data.add(accData)
        this.setAccData(data as ArrayList<Any>)
    }

    suspend fun subscribeToAccelerometer(interval: Double) {
        val accelerometer: MeasurableSensor
        accelerometer = Accelerometer(this.context, interval)
        accelerometer.startListening()
        //Make this async, already in coroutine scope though?
        accelerometer.setOnSensorValuesChangedListener {
//            runBlocking {
//                val acc =
//                    AccelerometerValues(it[0], it[1], it[2], Calendar.getInstance().timeInMillis)
//                this@TripManager.updateAccData(acc)
//            }

            CoroutineScope(Dispatchers.IO).launch {
                val acc =
                    AccelerometerValues(it[0], it[1], it[2], Calendar.getInstance().timeInMillis)
                this@TripManager.updateAccData(acc)
            }
        }
    }

    suspend fun subscribeToGyroscope(interval: Double) {
        gyroscope = Gyroscope(this.context, interval)
        gyroscope.startListening()
        gyroscope.setOnSensorValuesChangedListener {
//            runBlocking {
//                val gyro = GyroscopeValues(it[0], it[1], it[2], Calendar.getInstance().timeInMillis)
//                this@TripManager.updateGyroscopeData(gyro)
//            }
            CoroutineScope(Dispatchers.IO).launch {
                val gyro = GyroscopeValues(it[0], it[1], it[2], Calendar.getInstance().timeInMillis)
                this@TripManager.updateGyroscopeData(gyro)

            }
        }

    }

    suspend fun startUploadService() {
        val handler = Handler(Looper.getMainLooper()) // Wrong thread?
        handler.post(object : Runnable {
            override fun run() {
//                runBlocking {
//                    try {
//                        this@TripManager.uploadTripData()
//                    } catch (e: Exception) {
//                    }
//                }
               val job = CoroutineScope(Dispatchers.IO).launch {
                    try {
                        this@TripManager.uploadTripData()
                    } catch (e: Exception) {
                    }
                }
//                job.cancel()
                handler.postDelayed(this, 30000)
            }
        })
    }

    private suspend fun getOrgPublicSettings(): OrgSettings {
        // TODO Get URL from elsewhere
        val res = Json.decodeFromString<OrgSettings>(
            this.api.makeRequest(
                "GET",
                urlOverride = "https://dev-eu-1.nsurely-motor.com/v1/api/public/dev-env-eu-west1/?telematics=true",
                data = null,
                params = null,
                headers = null,
                endpoint = ""
            ).readText()
        )

        Log.e("Public", res.toString())

        return res
    }


    suspend fun startFakeTrip() {
        val handler = Handler(Looper.getMainLooper()) // Wrong thread?

        handler.post(object : Runnable {
            override fun run() {
                createFakeTrip()
                handler.postDelayed(this, 1000)
            }
        })
    }

    // Call on interval
    fun createFakeTrip() {

        val a = storageManager.getInt("Activity")
        if (a != null) {
            Log.e("Act", a.toString())
        }

        val l = a?.let {
            LocationData(
                a = 12f,
                acc = 2f,
                alt = 2.2,
                altAcc = 4f,
                at = it,
                b = 2f,
                ts = System.currentTimeMillis() / 1000,
                baAcc = 2f,
                lat = 12.0,
                lng = 13.0,
                s = 2f,
                sAcc = 2f,
                va = 2f
            )
        }
    }

    // Make a suspend function?
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            var dist = 0.0

            if (intent.action == "Location") {
                CoroutineScope(testDispatcher).launch {

//                suspend fun foo() = withContext(Dispatchers.IO) {
                    // TODO change the default
                    val extras = intent.extras

                    // If GPS is null, throw Exception or just skip?
                    val lat = extras!!.getDouble("lat")
                    val lng = extras.getDouble("lng")
                    this@TripManager.tripActive = true
                    // This if statement doesn't really make sense
                    if (this@TripManager.tripActive) {

                        try {
                            val stats = this@TripManager.getTripStatistics()

                            stats.capturedPoints++
                            stats.distanceKm += getDistanceFromLatLonInKm(
                                stats.lastLat,
                                stats.lastLng,
                                lat,
                                lng
                            )
                            stats.lastLng = lng
                            stats.lastLat = lat

                            this@TripManager.setTripStatistics(stats)
                            if (stats.distanceKm > 0.01) {
                                dist = stats.distanceKm
                            }
                        } catch (e: Exception){
                            this@TripManager.startUploadService()
                            val stats = TripStatistics(
                                123,
                                123,
                                Calendar.getInstance().timeInMillis,
                                Calendar.getInstance().timeInMillis,
                                0.0,
                                1,
                                lat,
                                lng,
                                lat,
                                lng,
                                1,
                                1,
                                false
                            )
                            this@TripManager.setTripStatistics(stats)
                        }
                        val l = storageManager.getInt("Activity")?.let {
                            LocationData(
                                a = 12f,
                                acc = 2f,
                                alt = 2.2,
                                altAcc = 4f,
                                at = it,
                                b = 2f,
                                ts = Calendar.getInstance().timeInMillis,
                                baAcc = 2f,
                                lat = lat,
                                lng = lng,
                                s = 2f,
                                sAcc = 2f,
                                va = 2f
                            )
                        }
                        val data =
                            storageManager.getListObject("tripDetection", LocationData::class.java)

                        data.add(l as Any)

                        storageManager.putListObject("tripDetection", data)

                        this@TripManager.updateLocationArray(l)

                        val s = this@TripManager.getTripStatistics()


                        Log.e("GPS", "GPS")
                    } else {
                        // Updatelocationdata()
                        // Polyline data?
                            this@TripManager.setNotification(
                                "Trip Active",
                                "%.1f".format(dist) + "km travelled so far"
                            )
                            // If trip statistics have not been already set

                    }
                }
            }
        }
    }
}

