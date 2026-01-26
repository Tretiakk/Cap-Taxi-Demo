package com.cap.taxi.domain.data

import com.cap.taxi.domain.model.taxi.MapLocationInfo

interface IUserLocation {

    suspend fun getUserLocation(): MapLocationInfo?
}