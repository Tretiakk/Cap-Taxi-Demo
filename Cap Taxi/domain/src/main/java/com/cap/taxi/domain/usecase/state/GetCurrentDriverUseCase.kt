package com.cap.taxi.domain.usecase.state

import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.core.TaxiCore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetCurrentDriverUseCase(private val state: TaxiCore) {

    fun execute(): StateFlow<DriverInfo?> {
        return state.currentDriver.asStateFlow()
    }
}