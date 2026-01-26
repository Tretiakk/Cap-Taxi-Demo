package com.cap.taxi.domain.usecase.taxi

import com.cap.taxi.domain.core.TaxiCore

class CalculateRoutePriceUc(private val state: TaxiCore) {
    
    fun execute(): Int? {
        val curRoute = state.currentRoute.value
        val curDriver = state.currentDriver.value

        // Pre-check
        if (curRoute == null || curDriver == null) {
            return null
        }

        val distanceInMeters = curRoute.tripDistance

        if (distanceInMeters <= 0L) {
            return null
        }

        // Calculate
        val distanceInKilometers = distanceInMeters / 1000

        when (distanceInKilometers) {
            in 0..<5 -> {
                return curDriver.tariffS
            }

            in 5..<10 -> {
                return curDriver.tariffM
            }

            in 10..15 -> {
                return curDriver.tariffH
            }

            // distanceInKilometers > 15
            else -> return null
        }
    }
}