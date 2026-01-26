package com.cap.taxi.data

import com.cap.taxi.data.api.GooglePlacesService
import com.cap.taxi.data.api.PlacesApiImpl
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import java.util.Locale

// TODO
class PlacesApiImplTest {

    private val service: GooglePlacesService = mockk()
    private val net: IsNetworkConnectionUc = mockk()

    private lateinit var api: PlacesApiImpl
    private var oldLocale: Locale? = null

    @Before fun setUp() {
        api = PlacesApiImpl(service, net)
        oldLocale = Locale.getDefault()
    }

    @After fun tearDown() {
        oldLocale?.let { Locale.setDefault(it) }
    }

    @Test fun `makePlaceSearchRequest(text) throws when no internet`() = runTest {
        coEvery { net.execute() } returns false
        assertFailsWith<RuntimeException> { api.makePlaceSearchRequest("kyiv") }
    }

    @Test fun `makePlaceSearchRequest(text) maps predictions`() = runTest {
        Locale.setDefault(Locale("uk")) // supported in list -> should pass "uk"
        coEvery { net.execute() } returns true

        coEvery {
            service.getPredictions("kyiv", "uk", BuildConfig.GOOGLE_MAP_KEY)
        } returns mockk {
            every { body() } returns mockk {
                every { predictions } returns listOf(
                    mockk {
                        every { description } returns "Kyiv, Ukraine"
                        every { place_id } returns "p1"
                    },
                    mockk {
                        every { description } returns "Kyivska"
                        every { place_id } returns "p2"
                    }
                )
            }
        }

        val res = api.makePlaceSearchRequest("kyiv")

        assertEquals(
            listOf(
                MapPlaceInfo("Kyiv, Ukraine", "p1"),
                MapPlaceInfo("Kyivska", "p2")
            ),
            res
        )
        coVerify(exactly = 1) { service.getPredictions("kyiv", "uk", BuildConfig.GOOGLE_MAP_KEY) }
    }

    @Test fun `makePlaceSearchRequest(text) returns empty when body null`() = runTest {
        Locale.setDefault(Locale("uk"))
        coEvery { net.execute() } returns true

        coEvery { service.getPredictions(any(), any(), any()) } returns mockk {
            every { body() } returns null
        }

        val res = api.makePlaceSearchRequest("x")
        assertTrue(res.isEmpty())
    }

    @Test fun `makePlaceSearchRequest(text, location) uses circle param and maps`() = runTest {
        Locale.setDefault(Locale("uk"))
        coEvery { net.execute() } returns true

        val loc = MapLocationInfo(49.0, 24.0)
        val circle = "circle:10000@${loc.latitude},${loc.longitude}"

        coEvery {
            service.getPredictions("cafe", circle, "uk", BuildConfig.GOOGLE_MAP_KEY)
        } returns mockk {
            every { body() } returns mockk {
                every { predictions } returns listOf(
                    mockk {
                        every { description } returns "Cafe A"
                        every { place_id } returns "idA"
                    }
                )
            }
        }

        val res = api.makePlaceSearchRequest("cafe", loc)

        assertEquals(listOf(MapPlaceInfo("Cafe A", "idA")), res)
        coVerify(exactly = 1) { service.getPredictions("cafe", circle, "uk", BuildConfig.GOOGLE_MAP_KEY) }
    }

    @Test fun `makePlaceInfoRequest returns lat-lng`() = runTest {
        coEvery { net.execute() } returns true

        coEvery {
            service.getPlaceInfo("pid", "address_components,geometry", BuildConfig.GOOGLE_MAP_KEY)
        } returns mockk {
            every { body() } returns mockk {
                every { result.geometry.location.lat } returns 1.23
                every { result.geometry.location.lng } returns 4.56
            }
        }

        val res = api.makePlaceInfoRequest("pid")

        assertEquals(MapLocationInfo(1.23, 4.56), res)
        coVerify(exactly = 1) {
            service.getPlaceInfo("pid", "address_components,geometry", BuildConfig.GOOGLE_MAP_KEY)
        }
    }

    @Test fun `makePlaceInfoRequest throws when no internet`() = runTest {
        coEvery { net.execute() } returns false
        assertFailsWith<RuntimeException> { api.makePlaceInfoRequest("pid") }
    }
}