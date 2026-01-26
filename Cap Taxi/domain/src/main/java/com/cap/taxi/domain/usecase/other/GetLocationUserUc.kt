package com.cap.taxi.domain.usecase.other

import com.cap.taxi.domain.data.IUserLocation
import com.cap.taxi.domain.model.taxi.MapLocationInfo

class GetLocationUserUc(private val userLocation: IUserLocation){

    suspend fun execute(): MapLocationInfo? {
        return userLocation.getUserLocation()
    }
}