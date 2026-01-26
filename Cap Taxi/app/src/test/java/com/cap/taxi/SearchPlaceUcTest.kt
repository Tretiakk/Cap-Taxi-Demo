package com.cap.taxi

import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.usecase.taxi.SearchPlaceUc
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchPlaceUcTest {

    private val placesApi: IPlacesApi = mockk()
    private val uc = SearchPlaceUc(placesApi)

    @Test
    fun `execute delegates to placesApi`() {
        CoroutineScope(Dispatchers.Default).launch {
            val expected = listOf(MapPlaceInfo("Kyiv", "p1"), MapPlaceInfo("Lviv", "p2"))
            coEvery { placesApi.makePlaceSearchRequest("k") } returns expected

            val res = uc.execute("k")

            assertEquals(expected, res)
            coVerify(exactly = 1) { placesApi.makePlaceSearchRequest("k") }
        }
    }
}
