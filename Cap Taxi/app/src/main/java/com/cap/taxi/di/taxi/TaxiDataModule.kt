package com.cap.taxi.di.taxi

import com.cap.taxi.data.api.PlacesApiImpl
import com.cap.taxi.data.api.RouteApiImpl
import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.data.IRouteApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class TaxiDataModule {

    @Binds
    abstract fun bindIRouteApi(routeApiImpl: RouteApiImpl): IRouteApi

    @Binds
    abstract fun bindIPlacesApi(placesApiImpl: PlacesApiImpl): IPlacesApi
}