package com.icabanas.parkinggaragechallenge.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.api.ParkingService
import com.icabanas.parkinggaragechallenge.cache.ParkingMemoryCache
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Parking objects
 */
@Singleton
class ParkingRepository @Inject constructor(
        private val parkingService: ParkingService,
        private val parkingMemoryCache: ParkingMemoryCache
) {
    @Inject
    lateinit var context: Context

    fun loadParking(): MutableLiveData<Resource<Parking>> {
        val parking = MutableLiveData<Resource<Parking>>()

        //Update the Resource status to loading while performing background operations
        parking.value = Resource.loading(null)

        //If there is already a Parking object cached, return that one
        if (!parkingMemoryCache.hasParkingStored()) {
            //Enqueue the Retrofit API Call
            parkingService.getParking().enqueue(object : Callback<Parking> {
                override fun onResponse(call: Call<Parking>?, response: Response<Parking>?) {
                    if (response != null &&
                            response.isSuccessful &&
                            response.body() != null) {
                        parkingMemoryCache.storedParking = response.body()!!
                        parking.value = Resource.success(parkingMemoryCache.storedParking)
                    } else {
                        parking.value = Resource.error(context.getString(R.string.network_error), null)
                    }
                }

                override fun onFailure(call: Call<Parking>?, t: Throwable?) {
                    parking.value = Resource.error(context.getString(R.string.network_error), null)
                }
            })
        } else {
            parking.value = Resource.success(parkingMemoryCache.storedParking)
        }

        return parking
    }

}