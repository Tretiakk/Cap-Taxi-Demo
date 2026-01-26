package com.cap.taxi.facade.impl

import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.usecase.other.GetLocationUserUc
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.domain.usecase.taxi.RouteMakeUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceInfoUc
import com.cap.taxi.domain.usecase.taxi.SearchPlacePreciseUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceUc
import com.cap.taxi.facade.TaxiModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.StateFlow

class TaxiModelImpl(
    taxiCore: TaxiCore,
    private val routeMakeUc: RouteMakeUc,
    private val searchPlaceInfoUc: SearchPlaceInfoUc,
    private val searchPlaceUc: SearchPlaceUc,
    private val searchPlacePreciseUc: SearchPlacePreciseUc,
    private val calculateRoutePriceUc: CalculateRoutePriceUc,
    private val getUserLocationUc: GetLocationUserUc
) : TaxiModel {

    override val currentRoute: StateFlow<RouteInfo?> = taxiCore.currentRoute
    override val currentDriver: StateFlow<DriverInfo?> = taxiCore.currentDriver

    override suspend fun putRoad(
        from: MapLocationInfo,
        to: MapLocationInfo
    ) {
        routeMakeUc.execute(from, to)
    }

    override suspend fun makePlaceInfoRequest(
        placeId: String
    ): MapLocationInfo? {
        return searchPlaceInfoUc.execute(placeId)
    }

    override suspend fun makePlaceSearchRequest(
        text: String,
        userLocation: MapLocationInfo?
    ): List<MapPlaceInfo> {
        if (userLocation == null) {
            return searchPlaceUc.execute(text)
        } else {
            return searchPlacePreciseUc.execute(text, userLocation)
        }
    }

    override fun getCurrentRoutePrice(): Int? {
        return calculateRoutePriceUc.execute()
    }

    override suspend fun getUserLocation(): MapLocationInfo? {
        return getUserLocationUc.execute()
    }

    override fun convertToLatLng(list: List<MapLocationInfo>): List<LatLng> {
        return super.convertToLatLng(list)
    }
}