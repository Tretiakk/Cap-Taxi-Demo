package com.cap.taxi

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculateRoutePriceUcTest {

    private lateinit var taxiCore: TaxiCore
    private lateinit var calculateRoutePriceUc: CalculateRoutePriceUc
    private lateinit var dummyDriver: DriverInfo

    @Before
    fun setUp() {
        taxiCore = TaxiCore()
        calculateRoutePriceUc = CalculateRoutePriceUc(taxiCore)
        dummyDriver = DriverInfo(
            id = 1,
            name = "TestDriver",
            tariffH = 30,
            tariffM = 20,
            tariffS = 10,
            rate = 5.0f,
            car = "Car",
            typeOfCar = DriverCarType.SEDAN,
            experience = 10,
            phoneNumber = "123",
            image = null
        )
    }

    @Test
    fun execute_noRoute_returnsNull() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = null
        val result = calculateRoutePriceUc.execute()
        assertNull(result)
    }

    @Test
    fun execute_noDriver_returnsNull() {
        taxiCore.currentDriver.value = null
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 1000, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertNull(result)
    }

    @Test
    fun execute_distanceZero_returnsNull() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 0, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertNull(result)
    }

    @Test
    fun execute_distanceBelow5km_returnsTariffS() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 4000, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertEquals(10, result)
    }

    @Test
    fun execute_distanceBetween5And10km_returnsTariffM() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 5000, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertEquals(20, result)
    }

    @Test
    fun execute_distanceBetween10And15km_returnsTariffH() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 10000, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertEquals(30, result)
    }

    @Test
    fun execute_distanceAbove15km_returnsNull() {
        taxiCore.currentDriver.value = dummyDriver
        taxiCore.currentRoute.value = RouteInfo(tripDuration = 100, tripDistance = 16000, routeCurve = listOf())
        val result = calculateRoutePriceUc.execute()
        assertNull(result)
    }
}
