package com.icabanas.parkinggaragechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.icabanas.parkinggaragechallenge.cache.ParkingMemoryCache
import com.icabanas.parkinggaragechallenge.vo.Level
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Spots
 */
@Singleton
class SpotsRepository @Inject constructor(
        private val parkingMemoryCache: ParkingMemoryCache
) {

    fun loadSpot(levelId: Int, spotId: Int): LiveData<Spot> {
        val spot = MutableLiveData<Spot>()
        parkingMemoryCache.findSpot(levelId, spotId)?.let {
            spot.value = it
        }
        return spot
    }

    fun bookSpot(levelId: Int, spotId: Int, vehicle: Vehicle): Boolean {
        return parkingMemoryCache.bookSpot(levelId, spotId, vehicle)
    }

    fun releaseSpot(levelId: Int, spotId: Int): Boolean {
        return parkingMemoryCache.releaseSpot(levelId, spotId)
    }

    fun findFreeParkingSpot(size: Int) : LiveData<Spot> {
        val spot = MutableLiveData<Spot>()
        spot.value = parkingMemoryCache.findFreeParkingSpot(size)
        return spot
    }

}