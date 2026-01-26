package com.cap.taxi.data.di

import com.cap.taxi.data.api.GooglePlacesService
import com.cap.taxi.data.api.PlacesApiImpl
import com.cap.taxi.data.api.RouteApiImpl
import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.data.IRouteApi
import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class TaxiRepoModule {

    @Provides
    fun provideIRouteApi(): RouteApiImpl {
        return RouteApiImpl()
    }

    @Provides
    fun provideIPlacesApi(
        googlePlacesService: GooglePlacesService,
        isNetworkConnectionUc: IsNetworkConnectionUc
    ): PlacesApiImpl {
        return PlacesApiImpl(googlePlacesService, isNetworkConnectionUc)
    }

    @Provides
    fun provideGooglePlacesService(): GooglePlacesService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(GooglePlacesService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return  retrofit.create(GooglePlacesService::class.java)
    }
}