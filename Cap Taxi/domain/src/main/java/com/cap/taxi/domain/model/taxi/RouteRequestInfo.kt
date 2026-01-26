package com.cap.taxi.domain.model.taxi

data class RouteRequestInfo (
    val departure: MapLocationInfo?,
    val destination: MapLocationInfo?
)