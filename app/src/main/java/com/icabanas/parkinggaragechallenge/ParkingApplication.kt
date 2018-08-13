package com.icabanas.parkinggaragechallenge

import android.app.Application
import com.icabanas.parkinggaragechallenge.di.AppComponent
import com.icabanas.parkinggaragechallenge.di.AppModule
import com.icabanas.parkinggaragechallenge.di.DaggerAppComponent
import timber.log.Timber

class ParkingApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}