package com.cap.taxi.di.taxi

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.data.IRouteApi
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.domain.usecase.taxi.RouteMakeUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceInfoUc
import com.cap.taxi.domain.usecase.taxi.SearchPlacePreciseUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceUc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class TaxiUseCaseModule {

    @Provides
    fun provideRouteMakeUc(
        state: TaxiCore,
        routeApi: IRouteApi,
        calculateRoutePriceUc: CalculateRoutePriceUc
    ): RouteMakeUc {
        return RouteMakeUc(state, routeApi, calculateRoutePriceUc)
    }

    @Provides
    fun provideSearchPlaceInfoUc(placesApi: IPlacesApi): SearchPlaceInfoUc {
        return SearchPlaceInfoUc(placesApi)
    }

    @Provides
    fun provideSearchPlaceUc(placesApi: IPlacesApi): SearchPlaceUc {
        return SearchPlaceUc(placesApi)
    }

    @Provides
    fun provideSearchPlacePreciseUc(placesApi: IPlacesApi): SearchPlacePreciseUc {
        return SearchPlacePreciseUc(placesApi)
    }
}