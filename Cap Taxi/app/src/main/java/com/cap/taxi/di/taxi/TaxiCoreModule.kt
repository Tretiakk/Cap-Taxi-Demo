package com.cap.taxi.di.taxi

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.usecase.other.GetLocationUserUc
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.domain.usecase.taxi.RouteMakeUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceInfoUc
import com.cap.taxi.domain.usecase.taxi.SearchPlacePreciseUc
import com.cap.taxi.domain.usecase.taxi.SearchPlaceUc
import com.cap.taxi.facade.TaxiModel
import com.cap.taxi.facade.impl.TaxiModelImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class TaxiCoreModule {

    @Provides
    fun provideTaxiModel(
        taxiCore: TaxiCore,
        routeMakeUc: RouteMakeUc,
        searchPlaceInfoUc: SearchPlaceInfoUc,
        searchPlaceUc: SearchPlaceUc,
        searchPlacePreciseUc: SearchPlacePreciseUc,
        calculateRoutePriceUc: CalculateRoutePriceUc,
        getUserLocationUc: GetLocationUserUc
    ): TaxiModel {
        return TaxiModelImpl(
            taxiCore,
            routeMakeUc,
            searchPlaceInfoUc,
            searchPlaceUc,
            searchPlacePreciseUc,
            calculateRoutePriceUc,
            getUserLocationUc
        )
    }


}