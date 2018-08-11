package com.icabanas.parkinggaragechallenge.di

import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun providesViewModelFactory(parkingRepository: ParkingRepository): ParkingViewModelFactory {
        return ParkingViewModelFactory(parkingRepository)
    }

}

