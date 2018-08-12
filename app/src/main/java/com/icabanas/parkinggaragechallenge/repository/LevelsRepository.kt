package com.icabanas.parkinggaragechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.icabanas.parkinggaragechallenge.cache.ParkingMemoryCache
import com.icabanas.parkinggaragechallenge.vo.Level
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Levels
 */
@Singleton
class LevelsRepository @Inject constructor(
        private val parkingMemoryCache: ParkingMemoryCache
) {

    fun loadLevel(levelId: Int): LiveData<Level> {
        val level = MutableLiveData<Level>()
        parkingMemoryCache.findLevel(levelId)?.let {
            level.value = it
        }
        return level
    }

}