package com.cap.taxi.domain.model.taxi

data class RouteInfo(
    val tripDuration: Long, // Duration in seconds
    val tripDistance: Long, // Distance in meters
    val routeCurve: List<MapLocationInfo>,
    val priceOfRoute: Int? = null
)