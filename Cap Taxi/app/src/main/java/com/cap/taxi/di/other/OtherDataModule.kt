package com.cap.taxi.di.other

import com.cap.taxi.data.user.NetworkStateImpl
import com.cap.taxi.data.user.UserLocationImpl
import com.cap.taxi.domain.data.INetworkState
import com.cap.taxi.domain.data.IUserLocation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class OtherDataModule {

    @Binds
    abstract fun bindNetworkState(networkStateImpl: NetworkStateImpl): INetworkState

    @Binds
    abstract fun bindUserLocation(userLocationImpl: UserLocationImpl): IUserLocation
}