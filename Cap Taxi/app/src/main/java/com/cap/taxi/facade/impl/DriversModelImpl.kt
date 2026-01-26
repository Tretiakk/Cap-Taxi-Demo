package com.cap.taxi.facade.impl

import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.common.SearchDriverType
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.cap.taxi.domain.usecase.driver.DriverSelectUc
import com.cap.taxi.domain.usecase.driver.LoadDriversUc
import com.cap.taxi.domain.usecase.driver.SearchDriverRequestUc
import com.cap.taxi.domain.usecase.state.GetCurrentDriverUseCase
import com.cap.taxi.domain.usecase.state.GetCurrentRouteUseCase
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.facade.DriversModel
import com.cap.taxi.facade.states.DriverInfoUi
import com.cap.taxi.facade.states.toDomain
import com.cap.taxi.facade.states.toUi
import kotlinx.coroutines.flow.StateFlow

class DriversModelImpl(
    getCurrentDriverUc: GetCurrentDriverUseCase,
    getCurrentRouteUc: GetCurrentRouteUseCase,
    private val loadDriversUc: LoadDriversUc,
    private val driverSelectUc: DriverSelectUc,
    private val searchDriverRequestUc: SearchDriverRequestUc
) : DriversModel {

    override val currentDriver: StateFlow<DriverInfo?> = getCurrentDriverUc.execute()

    override fun selectDriver(driver: DriverInfoUi) {
        driverSelectUc.execute(driver.toDomain())
    }

    override fun searchForDrivers(
        text: String,
        typeOfSearch: SearchDriverType,
        typeOfCarBody: DriverCarType,
        onFail: (err: Int) -> Unit
    ): List<DriverInfoUi> {

        val driversFound =
            searchDriverRequestUc.execute(
                text,
                typeOfSearch,
                typeOfCarBody,
                onFail
            )

        return driversFound.map { it.toUi() }
    }

    override suspend fun loadDrivers(): List<DriverInfoUi> {
        return loadDriversUc.execute()
            .map { it.toUi() }
    }
}