package com.inaza.androidsdk.location

import kotlinx.serialization.Serializable


// Is uuid needed?

// Missing latAcc
// Missing lngAcc?
// a is PointAccuracy(?)

// Missing vaAcc
@Serializable
data class LocationData(
    val ts: Long,
    val lat: Double,
    val lng: Double,
    val a: Float,
    val at: Int,
    val alt: Double,
    val altAcc: Float,
    val s: Float,
    val sAcc: Float,
    val acc: Float?,
    val b: Float,
    val baAcc: Float,
    val va: Float?
)