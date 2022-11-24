package com.inaza.androidsdk.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class ActivityTransitionService : Service() {
    private val mBinder: IBinder = LocalBinder()
    private var transitions: MutableList<ActivityTransition>? = null
    private var request: ActivityTransitionRequest? = null
    private var mPendingIntent: PendingIntent? = null
    private var mIntentService: Intent? = null
    private var receiver: ActivityTransitionBroadcastReceiver? = null
    private val TRANSITIONS_RECEIVER_ACTION = "com.package.name.ACTION_PROCESS_ACTIVITY_TRANSITIONS"

    inner class LocalBinder : Binder() {
        val serverInstance: ActivityTransitionService
            get() = this@ActivityTransitionService
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.e("Act", " Started")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        transitions = ArrayList()
        receiver = ActivityTransitionBroadcastReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            receiver!!,
            IntentFilter(TRANSITIONS_RECEIVER_ACTION)
        )
        mIntentService = Intent(this, ActivityTransitionBroadcastReceiver::class.java)

        mPendingIntent = PendingIntent.getBroadcast(
            this, 1,
            mIntentService!!, PendingIntent.FLAG_MUTABLE
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        )
        (transitions as ArrayList<ActivityTransition>).add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        )
        request = ActivityTransitionRequest(transitions as ArrayList<ActivityTransition>)
        requestActivityUpdatesButtonHandler()


        if (Build.VERSION.SDK_INT >= 26) {

            // Might be redunadnt if channel is already started elsewhere?
//            val CHANNEL_ID = "my_channel_01"
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
//                channel
//            )
//            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Hey")
//                .setContentText("hi")

            val notification = NotificationCompat.Builder(this, "location")
                .setContentTitle("Activity Tracking...")
                .setContentText("")
                .setSmallIcon(androidx.core.R.drawable.notification_bg)
                .setOngoing(true)
            startForeground(1, notification.build())
        }
        sendBroadcast(mIntentService)
    }

    @SuppressLint("MissingPermission")
    fun requestActivityUpdatesButtonHandler() {

        // myPendingIntent is the instance of PendingIntent where the app receives callbacks.
        val task: Task<Void> = mPendingIntent?.let {
            ActivityRecognition.getClient(this)
                .requestActivityTransitionUpdates(request!!, it)
        } as Task<Void>
        task.addOnSuccessListener(

            OnSuccessListener<Void?> {
                Log.e("Act", " Activity service started")
            }
        )
        task.addOnFailureListener(
            OnFailureListener {
                // Handle error
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
//        removeActivityUpdatesButtonHandler()
    }
}
