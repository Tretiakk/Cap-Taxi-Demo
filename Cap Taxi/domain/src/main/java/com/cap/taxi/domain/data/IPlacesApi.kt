package com.cap.taxi.domain.data

import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo

interface IPlacesApi {

    suspend fun makePlaceSearchRequest(text: String): List<MapPlaceInfo>
    suspend fun makePlaceSearchRequest(text: String, location: MapLocationInfo): List<MapPlaceInfo>
    suspend fun makePlaceInfoRequest(placeId: String): MapLocationInfo?
}