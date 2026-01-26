package com.cap.taxi.domain.usecase.driver

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.data.IDriversDB
import com.cap.taxi.domain.model.driver.DriverInfo

class LoadDriversUc(
    private val state: TaxiCore,
    private val driversDB: IDriversDB
) {

    suspend fun execute(): List<DriverInfo> {
        val loadedDrivers = driversDB.getDrivers()

        state.allDrivers.addAll(loadedDrivers)

        return loadedDrivers
    }
}