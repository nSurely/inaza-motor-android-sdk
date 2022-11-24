package com.inaza.androidsdk.trips

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.inaza.androidsdk.activity.Activity
import com.inaza.androidsdk.activity.ActivityTransitionService
import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.helpers.getDistanceFromLatLonInKm
import com.inaza.androidsdk.location.LocationData
import com.inaza.androidsdk.location.LocationService
import com.inaza.androidsdk.location.TripStatistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import java.util.*

class AutoTripDetection(sourceId: String, api: ApiHandler, context: Context) :
    TripManager("efefe", 20f, context, api) {
    var coords: MutableList<Pair<Double,Double>> = mutableListOf<Pair<Double,Double>>()
    init {
        val intent = Intent(
            context,
            ActivityTransitionService::class.java
        )
        ContextCompat.startForegroundService(context, intent)


        Intent(context.applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.applicationContext.startService(this)
        }
        // Register the broadcast receiver for receiving location data
        val filter = IntentFilter("Location")
        context.registerReceiver(this, filter)
//
        CoroutineScope(Dispatchers.IO).launch {
            this@AutoTripDetection.orgSettings.telematics?.dataCapture?.gyroscopeInterval?.let {
                this@AutoTripDetection.subscribeToGyroscope(
                    it
                )
            }
            this@AutoTripDetection.orgSettings.telematics?.dataCapture?.accelerometerInterval?.let {
                this@AutoTripDetection.subscribeToAccelerometer(
                    it
                )
            }
        }
    }

    suspend fun startTripDetection() {
        // Function called when a when location changes, and trip not active.

        // trip detection location array?
//        this.updateTripDetectionLocationArray(location)

        // Filter out older locations


        // Get distance travelled in the last X minutes
        // From the filtered array ah
        var distanceTravelled = 0.0
        val tripDetectionLocations = this.getTripDetectionLocationArray()

        tripDetectionLocations.filter { loc ->
            loc.ts < ((System.currentTimeMillis() / 1000) - (30000))
        }
        Log.e("SIZE", "${tripDetectionLocations.size}")


        // TODO How often is this array cleared? Every 30 seconds when request is sent?
        for ((index, loc) in tripDetectionLocations.withIndex()) {
            if (index > 0) {
                distanceTravelled += getDistanceFromLatLonInKm(
                    loc.lat,
                    loc.lng,
                    tripDetectionLocations[index - 1].lat,
                    tripDetectionLocations[index - 1].lng
                )
            }
        }
        var activityInVehicle = 0
        var activityUnknown = 0

        for (loc in tripDetectionLocations) {
            // TODO change back to 'in_vehicle'

            if (Activity.fromInt(loc.at) == Activity.WALKING) {
                activityInVehicle++
            } else if (Activity.fromInt(loc.at) == Activity.UNKNOWN) {
                activityUnknown++
            }
        }

        val activityInVehiclePercentage =
            (activityInVehicle.toDouble() / tripDetectionLocations.size.toDouble()) * 100
        val activityUnknownPercentage =
            (activityUnknown.toDouble() / tripDetectionLocations.size.toDouble()) * 100

        // Get distance req from org settings
        // Change % back to 60

        this.startTrip("auto", tripDetectionLocations)
        this.tripActive = true
//        if (distanceTravelled > 0.01 && activityInVehiclePercentage > 5 || activityUnknownPercentage == 100.0) {
//            this.startTrip("auto", tripDetectionLocations)
//            this.tripActive = true
//        } else {
//            Log.e("Auto", distanceTravelled.toString())
//            Log.e("Auto", activityInVehiclePercentage.toString())
//        }


        //
//        let activityInVehicle = 0;
//        let activityUnknown = 0;
//
//        tripDetectionLocations.forEach(loc => {
//            if (loc.activity?.type == 'in_vehicle') {
//                activityInVehicle++;
//            } else if (loc.activity?.type == 'unknown') {
//                activityUnknown++;
//            }
//        });
//
//        let activityInVehiclePercentage = (activityInVehicle / tripDetectionLocations.length) * 100;
//        let activityUnknownPercentage = (activityUnknown / tripDetectionLocations.length) * 100;
//
//        if (
//            distanceTravelled > MotorApi.publicData.getRenderingOptions()?.telematics?.autoTracking?.distance / 1000 &&
//            (activityInVehiclePercentage > 60 || activityUnknownPercentage == 100)
//        ) {
//            try {
//                this.startTrip('auto', tripDetectionLocations);
//            } catch (e) {
//                console.log('[startTripDetection] Error', e);
//            }
//            this.setTripDetectionLocationArray([]);
//        }
//    }

        // Clear array afte trip start
        // check if in Vehicle or not
    }


    suspend fun endTripDetection() {
        // pushTripDetectionLocation?


        // Wait at least 10 minutes before ending trip
        if (this.getTripStatistics().startTime.plus(600000) < Clock.System.now().epochSeconds) {
            var distanceTravelled = 0.0
            val tripDetectionLocations = this.getTripDetectionLocationArray()

            // TODO change values here
            tripDetectionLocations.filter { loc ->
                loc.ts < ((System.currentTimeMillis() / 1000) - (30000))
            }

            // TODO How often is this array cleared? Every 30 seconds when request is sent?
            for ((index, loc) in tripDetectionLocations.withIndex()) {
                if (index > 0) {
                    distanceTravelled += getDistanceFromLatLonInKm(
                        loc.lat,
                        loc.lng,
                        tripDetectionLocations[index - 1].lat,
                        tripDetectionLocations[index - 1].lng
                    )
                }
            }
            var activityInVehicle = 0
            var activityUnknown = 0

            for (loc in tripDetectionLocations) {
                // TODO change back to 'in_vehicle'

                if (Activity.fromInt(loc.at) == Activity.WALKING) {
                    activityInVehicle++
                } else if (Activity.fromInt(loc.at) == Activity.UNKNOWN) {
                    activityUnknown++
                }
            }

            val activityInVehiclePercentage =
                (activityInVehicle.toDouble() / tripDetectionLocations.size.toDouble()) * 100
            val activityUnknownPercentage =
                (activityUnknown.toDouble() / tripDetectionLocations.size.toDouble()) * 100

            // TODO Change values here
            if (distanceTravelled < 0.1 && activityInVehiclePercentage < 40 || activityUnknownPercentage == 100.0) {
                this.endTrip("auto")
            }

        }


        var distanceTravelled = 0.0

//            for ((index, value) in tripDetectionLocations.withIndex()){
//                if(index > 0){
////                    value.
////                    distanceTravelled += getDistanceFromLatLonInKm(1.0, 1.0, 1.0, 1.0)
//                }
//            }
//        }
        this.endTrip("auto")
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        // Make this async
        if (intent != null) {
            if (intent.action == "Location") {
//                CoroutineScope(Dispatchers.Default).launch {
                runBlocking {
                    if (!this@AutoTripDetection.tripActive) {
                        this@AutoTripDetection.setNotification(
                            "Checking for driving activity",
                            "In order to keep your vehicle insured, ${this@AutoTripDetection.orgSettings.displayName} " +
                                    "periodically checks if you are driving"
                        )

                        val notification = context?.let {
                            NotificationCompat.Builder(it, "location")
                                .setContentTitle("TEST...")
                                .setContentText("")
                                .setSmallIcon(androidx.core.R.drawable.notification_bg)
                                .setOngoing(true)
                        }

                        if (notification != null) {
                            notification.build()
                        }
                    }

                    // TODO change the default
                    val extras = intent.extras

                    val lat = extras!!.getDouble("lat")
                    val lng = extras.getDouble("lng")

                    val coord = Pair<Double, Double>(lat,lng)
                    this@AutoTripDetection.coords.add(coord)

                    this@AutoTripDetection.storageManager.putListObject("coords", this@AutoTripDetection.coords as ArrayList<Any>)
                    Log.e("coords", this@AutoTripDetection.coords.toString())
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

//                val d = data as ArrayList<LocationData>

                    storageManager.putListObject("tripDetection", data)
                    this@AutoTripDetection.updateLocationArray(l)

                    Log.e("GPS", "GPS")
                    Log.e("SSERVICE", LocationService.isRunning.toString())

                    // TODO Negate tripActive !!!!!!!
                    if (!this@AutoTripDetection.tripActive) {
                        runBlocking {
                            this@AutoTripDetection.startTripDetection()
                        }

                    } else {
                        // Wild NaN appearing in here somewhere still
                        // Updatelocationdata()?
                        // Polyline data?
                        try {
                            val stats = this@AutoTripDetection.getTripStatistics()
                            stats.capturedPoints++
                            stats.distanceKm += getDistanceFromLatLonInKm(
                                stats.lastLat,
                                stats.lastLng,
                                lat,
                                lng
                            )
                            stats.lastLng = lng
                            stats.lastLat = lat

                            this@AutoTripDetection.setTripStatistics(stats)

                            var dist = 0.0
                            if (stats.distanceKm > 0.01) {
                                dist = stats.distanceKm
                            }
                            this@AutoTripDetection.setNotification(
                                "Trip Active",
                                "%.1f".format(dist) + "km travelled so far",
                                34
                            )


                            // If trip statistics have not been already set
                        } catch (e: Exception) {
                            this@AutoTripDetection.startUploadService()
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
                            this@AutoTripDetection.setTripStatistics(stats)
                        }
                    }
                }
            }
        }
    }
}