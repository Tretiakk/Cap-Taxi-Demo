package com.cap.taxi

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.data.IRouteApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.domain.usecase.taxi.RouteMakeUc
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RouteMakeUcTest {

    private lateinit var taxiCore: TaxiCore
    private lateinit var routeApi: IRouteApi
    private lateinit var calculateRoutePriceUc: CalculateRoutePriceUc
    private lateinit var routeMakeUc: RouteMakeUc

    @Before
    fun setUp() {
        taxiCore = TaxiCore()
        routeApi = mockk()
        calculateRoutePriceUc = mockk()
        routeMakeUc = RouteMakeUc(taxiCore, routeApi, calculateRoutePriceUc)
    }

    @Test
    fun execute_noCurrentDriver_returnsCleanRouteAndUpdatesState() = runBlocking {
        taxiCore.currentDriver.value = null
        val from = MapLocationInfo(0.0, 0.0)
        val to = MapLocationInfo(1.0, 1.0)
        val route = RouteInfo(tripDuration = 100, tripDistance = 1000, routeCurve = listOf(), priceOfRoute = null)
        coEvery { routeApi.makeRoute(from, to) } returns route
        val result = routeMakeUc.execute(from, to)
        coVerify { routeApi.makeRoute(from, to) }
        verify(exactly = 0) { calculateRoutePriceUc.execute() }
        assertEquals(route, result)
        assertEquals(route, taxiCore.currentRoute.value)
        assertNull(result.priceOfRoute)
    }

    @Test
    fun execute_withCurrentDriver_callsCalculateRoutePriceAndReturnsPricedRoute() = runBlocking {
        val driver = DriverInfo(
            1, "Test",
            tariffH = 100, tariffM = 50, tariffS = 20,
            rate = 5.0f, car = "Car",
            typeOfCar = DriverCarType.SEDAN, experience = 5,
            phoneNumber = "123", image = null
        )
        taxiCore.currentDriver.value = driver
        val from = MapLocationInfo(0.0, 0.0)
        val to = MapLocationInfo(1.0, 1.0)
        val route = RouteInfo(tripDuration = 200, tripDistance = 5000, routeCurve = listOf(), priceOfRoute = null)
        coEvery { routeApi.makeRoute(from, to) } returns route
        every { calculateRoutePriceUc.execute() } returns 42
        val result = routeMakeUc.execute(from, to)
        coVerify { routeApi.makeRoute(from, to) }
        verify { calculateRoutePriceUc.execute() }
        assertNotNull(result.priceOfRoute)
        assertEquals(42, result.priceOfRoute)
        assertEquals(result, taxiCore.currentRoute.value)
    }
}
