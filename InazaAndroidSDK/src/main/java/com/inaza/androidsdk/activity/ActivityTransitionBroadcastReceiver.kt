package com.inaza.androidsdk.activity

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.ActivityTransitionResult
import com.inaza.androidsdk.helpers.StorageManager


open class ActivityTransitionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val storageManager = StorageManager(context)

        // This is not working for some reason, both with context & appContext.
        // At least in testing, seems to work okay otherwise... but why
        // It's an odd one, must be due how the broadcast receiver is registered?
        val i = intent?.extras
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = intent?.let { ActivityTransitionResult.extractResult(it) }!!
            for (event in result.transitionEvents) {
//                val activity = Activity.fromInt(event.activityType).toString().lowercase()
                val activity = event.activityType
                val state = Transition.fromInt(event.transitionType).toString().lowercase()

//                if (state != storageManager.getString("Activity")) {
//                    val notification = context.let {
//                        NotificationCompat.Builder(it, "trip")
//                            .setContentTitle("Activity")
//                            .setContentText("You have $state $activity")
//                            .setSmallIcon(androidx.core.R.drawable.notification_bg)
//                            .setOngoing(true)
//                    }
//                    notificationManager.notify(4, notification.build())
//
//                    storageManager.putInt("Activity", activity)
//                    Log.e("activity", Activity.fromInt(activity).toString())
//                }
//            }
                storageManager.putInt("Activity", activity)
                Log.e("activity", Activity.fromInt(activity).toString())
            }
        }
    }
}

enum class Activity(val value: Int) {
    IN_VEHICLE(0),
    ON_BICYCLE(1),
    ON_FOOT(2),
    STILL(3),
    UNKNOWN(4),
    TILTING(5),
    WALKING(7),
    RUNNING(8);

    companion object {
        fun fromInt(value: Int) = Activity.values().first { it.value == value }
    }
}

enum class Transition(val value: Int) {
    Started(0),
    Stopped(1);

    companion object {
        fun fromInt(value: Int) = Transition.values().first { it.value == value }
    }
}
