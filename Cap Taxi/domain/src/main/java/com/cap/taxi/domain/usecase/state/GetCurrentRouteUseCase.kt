package com.cap.taxi.domain.usecase.state

import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.model.taxi.RouteInfo
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetCurrentRouteUseCase(private val state: TaxiCore) {

    fun execute(): StateFlow<RouteInfo?> {
        return state.currentRoute.asStateFlow()
    }
}