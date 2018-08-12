package com.icabanas.parkinggaragechallenge.di

import com.icabanas.parkinggaragechallenge.MainActivity
import com.icabanas.parkinggaragechallenge.ui.book.BookSpotActivity
import com.icabanas.parkinggaragechallenge.ui.spots.SpotsActivity
import com.icabanas.parkinggaragechallenge.ui.spots.detail.SpotDetailActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(spotsActivity: SpotsActivity)
    fun inject(spotDetailActivity: SpotDetailActivity)
    fun inject(bookSpotActivity: BookSpotActivity)
}