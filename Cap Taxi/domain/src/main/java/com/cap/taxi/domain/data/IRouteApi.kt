package com.cap.taxi.domain.data

import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.RouteInfo

interface IRouteApi {

    suspend fun makeRoute(
        from: MapLocationInfo,
        to: MapLocationInfo,
    ): RouteInfo
}