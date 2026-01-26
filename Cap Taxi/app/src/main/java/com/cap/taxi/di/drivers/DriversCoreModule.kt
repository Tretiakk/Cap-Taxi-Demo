package com.cap.taxi.di.drivers

import com.cap.taxi.domain.usecase.driver.DriverSelectUc
import com.cap.taxi.domain.usecase.driver.LoadDriversUc
import com.cap.taxi.domain.usecase.driver.SearchDriverRequestUc
import com.cap.taxi.domain.usecase.state.GetCurrentDriverUseCase
import com.cap.taxi.domain.usecase.state.GetCurrentRouteUseCase
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import com.cap.taxi.facade.DriversModel
import com.cap.taxi.facade.impl.DriversModelImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DriversCoreModule {

    @Provides
    fun provideDriversModel(
        getCurrentDriverUc: GetCurrentDriverUseCase,
        getCurrentRouteUc: GetCurrentRouteUseCase,
        loadDriversUc: LoadDriversUc,
        driverSelectUc: DriverSelectUc,
        searchDriverRequestUc: SearchDriverRequestUc
    ): DriversModel {
        return DriversModelImpl(
            getCurrentDriverUc,
            getCurrentRouteUc,
            loadDriversUc,
            driverSelectUc,
            searchDriverRequestUc
        )
    }
}