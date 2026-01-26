package com.cap.taxi.domain.usecase.taxi

import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo

class SearchPlaceInfoUc(private val placesApi: IPlacesApi) {

    suspend fun execute(placeId: String): MapLocationInfo? {
        return placesApi.makePlaceInfoRequest(placeId)
    }
}