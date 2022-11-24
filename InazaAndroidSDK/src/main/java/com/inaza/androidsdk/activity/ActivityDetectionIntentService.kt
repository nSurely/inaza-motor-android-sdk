//package com.example.inazaandroidsdk.activity
//
//import android.app.IntentService
//import android.content.Intent
//import androidx.localbroadcastmanager.content.LocalBroadcastManager
//import com.google.android.gms.location.ActivityRecognitionResult
//import com.google.android.gms.location.DetectedActivity
//
//@Suppress("DEPRECATION")
//class ActivityDetectionIntentService :
//    IntentService(ActivityDetectionIntentService::class.simpleName) {
//
//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun onHandleIntent(intent: Intent?) {
//        val result = intent?.let { ActivityRecognitionResult.extractResult(it) }
//        val detectedActivities: ArrayList<*> = result?.probableActivities as ArrayList<*>
//
//        for (activity in detectedActivities) {
//            broadcastActivity(activity!! as DetectedActivity)
//        }
//    }
//
//    private fun broadcastActivity(activity: DetectedActivity) {
//        val intent = Intent(Constants.BROADCAST_DETECTED_ACTIVITY)
//        intent.putExtra("type", activity.type)
//        intent.putExtra("confidence", activity.confidence)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//    }
//}
//
//object Constants {
//    const val BROADCAST_DETECTED_ACTIVITY = "activity_intent"
//    const val DETECTION_INTERVAL_IN_MILLISECONDS: Long = 5000
//    const val CONFIDENCE = 70
//}
