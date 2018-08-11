package com.icabanas.parkinggaragechallenge.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParkingMemoryCache @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) {

    private val TAG = "PARKING"

    /**
     * Stored [Parking] pulled directly from the Shared Preferences
     */
    var storedParking:Parking
        get() = gson.fromJson(sharedPreferences.getString(TAG, "{}"), Parking::class.java)
        set(value) {
            sharedPreferences.edit().putString(TAG, gson.toJson(value)).commit()
        }

    /**
     * Checks if a [Parking] was already stored in memory
     * @return true if there is a [Parking] object stored, false if not
     */
    fun hasParkingStored(): Boolean {
        return sharedPreferences.contains(TAG)
    }

    /**
     * Books a [Spot]
     * @return true if there is a [Spot] was successful, false if not
     */
    fun bookSpot(levelId:Int, spotId: Int, vehicle: Vehicle): Boolean {
        var booked = false
        if (hasParkingStored()) {
            var mutableParking = storedParking.copy()
            var filteredLevels = mutableParking.levels.filter { it.id == levelId }
            if (filteredLevels.isNotEmpty()) {
                var filteredSpots = filteredLevels.first().spots.filter { it.id == spotId }
                if (filteredSpots.isNotEmpty() && filteredSpots.first().vehicle == null) {
                    filteredSpots.first().vehicle = vehicle
                    storedParking = mutableParking
                    booked = true
                }
            }
        }
        return booked
    }

    /**
     * Releases a [Spot]
     * @return true if there the [Spot] was released, false if not
     */
    fun releaseSpot(levelId:Int, spotId: Int): Boolean {
        var released = false
        if (hasParkingStored()) {
            var mutableParking = storedParking.copy()
            var filteredLevels = mutableParking.levels.filter { it.id == levelId }
            if (filteredLevels.isNotEmpty()) {
                var filteredSpots = filteredLevels.first().spots.filter { it.id == spotId }
                if (filteredSpots.isNotEmpty()) {
                    filteredSpots.first().vehicle = null
                    storedParking = mutableParking
                    released = true
                }
            }
        }
        return released
    }

    /**
     * Searchs for the first empty [Spot] that can fit a specific vehicle space
     * @return a [Spot] if found, null if not
     */
    fun findFreeParkingSpot(size: Int): Spot? {
        var spot: Spot? = null
        //Iterate levels from lowest to highest
        for (level in storedParking.levels.sortedBy { it.id }) {
            //Filter taken and too small spots, then find best size fit
            val freeLevelSpots = level.spots.filter { spotIt -> spotIt.vehicle == null && spotIt.size >= size }.sortedBy { spotIt -> spotIt.size }
            if (freeLevelSpots.isNotEmpty()) {
                spot = freeLevelSpots.first()
                break
            }
        }
        return spot
    }


}