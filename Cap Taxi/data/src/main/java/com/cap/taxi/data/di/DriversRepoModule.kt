package com.cap.taxi.data.di

import android.content.Context
import com.cap.taxi.data.db.DriverDBStub
import com.cap.taxi.domain.data.IDriversDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DriversRepoModule {

    @Provides
    fun provideIDriversDB(
        @ApplicationContext context: Context
    ): DriverDBStub {
        return DriverDBStub(context)
    }
}