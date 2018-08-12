package com.icabanas.parkinggaragechallenge.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.icabanas.parkinggaragechallenge.BuildConfig
import com.icabanas.parkinggaragechallenge.api.ParkingService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .setLenient()
                    .create()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideRemoteParkingService(retrofit: Retrofit): ParkingService =
            retrofit.create(ParkingService::class.java)

}