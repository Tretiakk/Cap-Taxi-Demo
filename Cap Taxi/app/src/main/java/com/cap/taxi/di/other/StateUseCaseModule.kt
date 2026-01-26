package com.cap.taxi.di.other

import com.cap.taxi.domain.core.TaxiCore
import com.cap.taxi.domain.usecase.state.GetCurrentDriverUseCase
import com.cap.taxi.domain.usecase.state.GetCurrentRouteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class StateUseCaseModule {

    @Provides
    fun provideGetCurrentRouteUseCase(taxiCore: TaxiCore): GetCurrentRouteUseCase {
        return GetCurrentRouteUseCase(taxiCore)
    }

    @Provides
    fun provideGetCurrentDriverUseCase(taxiCore: TaxiCore): GetCurrentDriverUseCase {
        return GetCurrentDriverUseCase(taxiCore)
    }
}