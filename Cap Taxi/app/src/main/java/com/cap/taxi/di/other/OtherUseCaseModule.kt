package com.cap.taxi.di.other

import com.cap.taxi.data.user.UserLocationImpl
import com.cap.taxi.domain.data.INetworkState
import com.cap.taxi.domain.data.IUserLocation
import com.cap.taxi.domain.usecase.other.GetLocationUserUc
import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class OtherUseCaseModule {


    @Provides
    fun bindIsNetworkConnectionUc(network: INetworkState): IsNetworkConnectionUc {
        return IsNetworkConnectionUc(network)
    }

    @Provides
    fun provideGetUserLocationUc(userLocation: IUserLocation): GetLocationUserUc {
        return GetLocationUserUc(userLocation)
    }
}