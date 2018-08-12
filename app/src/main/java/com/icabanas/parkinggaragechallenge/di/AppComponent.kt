package com.icabanas.parkinggaragechallenge.di

import com.icabanas.parkinggaragechallenge.MainActivity
import com.icabanas.parkinggaragechallenge.ui.spots.SpotsActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(spotsActivity: SpotsActivity)
}