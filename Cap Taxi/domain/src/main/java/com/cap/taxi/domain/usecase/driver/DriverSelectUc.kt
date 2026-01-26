package com.cap.taxi.domain.usecase.driver

import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc

class DriverSelectUc(
    private val state: TaxiCore,
    private val calculateRoutePriceUc: CalculateRoutePriceUc
) {

    fun execute(driver: DriverInfo) {
        state.currentDriver.value = driver

        // Update route price
        if (state.currentRoute.value != null) {
            val update = state.currentRoute.value!!.copy(priceOfRoute = calculateRoutePriceUc.execute())

            state.currentRoute.value = update
        }
    }
}