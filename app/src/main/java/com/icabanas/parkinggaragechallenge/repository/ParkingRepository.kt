package com.icabanas.parkinggaragechallenge.repository

import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Spot

/**
 * Repository that handles Parking objects
 */
class ParkingRepository {


    /**
     * Searchs for the first empty [Spot] that can fit a specific vehicle space
     * @return a [Spot] if found, null if not
     */
    fun findFreeParkingSpot(size: Int, parking: Parking): Spot? {
        var spot: Spot? = null
        //Iterate levels from lowest to highest
        for (level in parking.levels.sortedBy { it.id }) {
            //Filter taken and too small spots, then find best size fit
            val freeLevelSpots = level.spots.filter { spot -> spot.vehicle == null && spot.size >= size }.sortedBy { it.size }
            if (freeLevelSpots.isNotEmpty()) {
                spot = freeLevelSpots.first()
                break
            }
        }
        return spot
    }
}