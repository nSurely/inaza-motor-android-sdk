package com.inaza.androidsdk.helpers

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.inaza.androidsdk.location.LocationService


class ServiceChecker
{
}

fun checkServiceRunning(): Boolean {
    return LocationService.isRunning
}

