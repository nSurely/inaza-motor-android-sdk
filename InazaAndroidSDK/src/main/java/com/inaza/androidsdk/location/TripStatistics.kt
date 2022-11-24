package com.inaza.androidsdk.location

data class TripStatistics(
    val tripId: Long,
    val sourceId: Long,
    val startTime: Long,
    val endTime: Long,
    var distanceKm: Double,
    var capturedPoints: Int,
    var startLat: Double,
    var startLng: Double,
    var lastLat: Double,
    var lastLng: Double,
    val backgroundLocationCount: Int,
    val foregroundLocationCount: Int,
    val seenByUser: Boolean = false,
) {
}