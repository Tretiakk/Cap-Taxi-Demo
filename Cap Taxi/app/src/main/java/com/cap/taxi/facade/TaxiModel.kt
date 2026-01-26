package com.cap.taxi.facade

import android.content.Context
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.StateFlow

interface TaxiModel {

    val currentRoute: StateFlow<RouteInfo?>
    val currentDriver: StateFlow<DriverInfo?>

    suspend fun putRoad(
        from: MapLocationInfo,
        to: MapLocationInfo
    )

    suspend fun makePlaceInfoRequest(placeId: String): MapLocationInfo?

    suspend fun makePlaceSearchRequest(text: String, userLocation: MapLocationInfo?): List<MapPlaceInfo>

    fun getCurrentRoutePrice(): Int?

    suspend fun getUserLocation(): MapLocationInfo?

    fun convertToLatLng(list: List<MapLocationInfo>): List<LatLng> {
        return list.map {
            LatLng(
                it.latitude,
                it.longitude
            )
        }
    }
}