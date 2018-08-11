package com.icabanas.parkinggaragechallenge.repository

import com.icabanas.parkinggaragechallenge.api.ParkingService
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Spot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Parking objects
 */
@Singleton
class ParkingRepository @Inject constructor(
        private val parkingService: ParkingService
)
{

    fun getParking() {
        parkingService.getParking().enqueue(object : Callback<Parking> {
            override fun onResponse(call: Call<Parking>?, response: Response<Parking>?) {
                if (response != null && response.isSuccessful) {
                    Timber.d(response.toString())
                } else {
                    Timber.e("ERROR - RESPONSE NULL")
                }
            }

            override fun onFailure(call: Call<Parking>?, t: Throwable?) {
                Timber.e(t)
            }
        })
    }

    /**
     * Searchs for the first empty [Spot] that can fit a specific vehicle space
     * @return a [Spot] if found, null if not
     */
    fun findFreeParkingSpot(size: Int, parking: Parking): Spot? {
        var spot: Spot? = null
        //Iterate levels from lowest to highest
        for (level in parking.levels.sortedBy { it.id }) {
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