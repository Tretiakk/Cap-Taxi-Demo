package com.cap.taxi.di.drivers

import com.cap.taxi.data.db.DriverDBStub
import com.cap.taxi.domain.data.IDriversDB
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DriversDataModule {

    @Binds
    abstract fun bindDriverDB(driversDriverDBStub: DriverDBStub): IDriversDB
}
