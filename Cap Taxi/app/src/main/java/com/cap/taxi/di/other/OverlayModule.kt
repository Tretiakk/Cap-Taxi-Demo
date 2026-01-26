package com.cap.taxi.di.other

import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import com.cap.taxi.facade.OverlayController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class OverlayModule {

    @Provides
    @Singleton
    fun provideOverlayController(
        isNetworkConnectionUc: IsNetworkConnectionUc
    ): OverlayController {
        return OverlayController(isNetworkConnectionUc)
    }
}