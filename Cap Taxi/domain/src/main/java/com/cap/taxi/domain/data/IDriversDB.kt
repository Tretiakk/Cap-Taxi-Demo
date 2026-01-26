package com.cap.taxi.domain.data

import com.cap.taxi.domain.model.driver.DriverInfo
import java.io.File

interface IDriversDB {

    suspend fun getDrivers(): List<DriverInfo>
}