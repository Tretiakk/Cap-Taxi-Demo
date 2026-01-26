package com.cap.taxi.data.di

import android.content.Context
import com.cap.taxi.data.user.NetworkStateImpl
import com.cap.taxi.data.user.UserLocationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class OtherRepoModule {

    @Provides
    fun provideNetworkStateImpl(
        @ApplicationContext context: Context
    ): NetworkStateImpl {
        return NetworkStateImpl(context)
    }

    @Provides
    fun provideUserLocationImpl(
        @ApplicationContext context: Context
    ): UserLocationImpl {
        return UserLocationImpl(context)
    }
}