package com.cap.taxi.domain.usecase.taxi

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.data.IRouteApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.RouteInfo

class RouteMakeUc(
    private val state: TaxiCore,
    private val routeApi: IRouteApi,
    private val calculateRoutePriceUc: CalculateRoutePriceUc
) {

    suspend fun execute(
        from: MapLocationInfo,
        to: MapLocationInfo
    ): RouteInfo {
        val route = routeApi.makeRoute(from, to)

        if (state.currentDriver.value != null) {
            val routeWithPrice = route.copy(priceOfRoute = calculateRoutePriceUc.execute())

            state.currentRoute.value = routeWithPrice
            return routeWithPrice
        }

        state.currentRoute.value = route
        return route
    }
}