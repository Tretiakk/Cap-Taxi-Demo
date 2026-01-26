package com.cap.taxi.domain.core

import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import kotlinx.coroutines.flow.MutableStateFlow

/*
 * Holds common states
 */
class TaxiCore {
    val allDrivers = ArrayList<DriverInfo>()

    val currentRoute = MutableStateFlow<RouteInfo?>(null)
    val currentDriver = MutableStateFlow<DriverInfo?>(null)
}