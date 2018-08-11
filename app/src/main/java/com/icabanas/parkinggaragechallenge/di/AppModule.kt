package com.icabanas.parkinggaragechallenge.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.icabanas.parkinggaragechallenge.ParkingApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val parkingApplication: ParkingApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = parkingApplication

    @Provides
    @Singleton
    fun provideSharePreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

}
