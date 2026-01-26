package com.cap.taxi.di.taxi

import com.cap.taxi.domain.core.TaxiCore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TaxiDomainModule {

    @Provides
    @Singleton
    fun provideTaxiCore(): TaxiCore {
        return TaxiCore()
    }
}