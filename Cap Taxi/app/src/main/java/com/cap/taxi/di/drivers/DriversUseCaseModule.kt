package com.cap.taxi.di.drivers

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.data.IDriversDB
import com.cap.taxi.domain.usecase.driver.DriverSelectUc
import com.cap.taxi.domain.usecase.driver.LoadDriversUc
import com.cap.taxi.domain.usecase.driver.SearchDriverRequestUc
import com.cap.taxi.domain.usecase.taxi.CalculateRoutePriceUc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DriversUseCaseModule {

    @Provides
    fun provideLoadDriversUc(
        taxiCore: TaxiCore,
        driversDB: IDriversDB
    ): LoadDriversUc {
        return LoadDriversUc(taxiCore, driversDB)
    }

    @Provides
    fun provideDriverSelectUc(
        taxiCore: TaxiCore,
        calculateRoutePriceUc: CalculateRoutePriceUc
    ): DriverSelectUc {
        return DriverSelectUc(taxiCore, calculateRoutePriceUc)
    }

    @Provides
    fun provideSearchDriverRequestUc(taxiCore: TaxiCore): SearchDriverRequestUc {
        return SearchDriverRequestUc(taxiCore)
    }

    @Provides
    fun provideCalculateRoutePriceUc(taxiCore: TaxiCore): CalculateRoutePriceUc {
        return CalculateRoutePriceUc(taxiCore)
    }

}