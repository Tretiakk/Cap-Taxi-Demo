package com.cap.taxi.domain.usecase.taxi

import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo

class SearchPlaceUc(private val placesApi: IPlacesApi) {

    suspend fun execute(text: String): List<MapPlaceInfo> {
        return placesApi.makePlaceSearchRequest(text)
    }
}