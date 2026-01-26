package com.cap.taxi.facade

import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.common.SearchDriverType
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.cap.taxi.facade.states.DriverInfoUi
import kotlinx.coroutines.flow.StateFlow

interface DriversModel {

    val currentDriver: StateFlow<DriverInfo?>

    fun selectDriver(driver: DriverInfoUi)

    fun searchForDrivers(
        request: String,
        typeOfSearch: SearchDriverType,
        typeOfCarBody: DriverCarType,
        onFail: (err: Int) -> Unit
    ): List<DriverInfoUi>

    suspend fun loadDrivers(): List<DriverInfoUi>
}