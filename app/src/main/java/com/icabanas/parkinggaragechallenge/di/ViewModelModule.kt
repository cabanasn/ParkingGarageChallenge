package com.icabanas.parkinggaragechallenge.di

import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModelFactory
import com.icabanas.parkinggaragechallenge.ui.spots.SpotsViewModelFactory
import com.icabanas.parkinggaragechallenge.ui.spots.detail.SpotDetailViewModelFactory
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

    @Provides
    @Singleton
    fun providesSpotsViewModelFactory(levelsRepository: LevelsRepository): SpotsViewModelFactory {
        return SpotsViewModelFactory(levelsRepository)
    }

    @Provides
    @Singleton
    fun providesSpotDetailViewModelFactory(spotsRepository: SpotsRepository): SpotDetailViewModelFactory {
        return SpotDetailViewModelFactory(spotsRepository)
    }

}

