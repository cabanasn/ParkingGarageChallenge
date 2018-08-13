package com.icabanas.parkinggaragechallenge.repository

import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import com.google.gson.Gson
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

    @Inject
    lateinit var gson: Gson

    fun loadParking(): MediatorLiveData<Resource<Parking>> {
        val parking = MediatorLiveData<Resource<Parking>>()

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
                        handleNetworkRequestFailure(parking)
                    }
                }

                override fun onFailure(call: Call<Parking>?, t: Throwable?) {
                    handleNetworkRequestFailure(parking)
                }
            })
        } else {
            parking.value = Resource.success(parkingMemoryCache.storedParking)
        }

        return parking
    }

    /**
     * In case of Network failure, fetch data from offline json
     */
    fun handleNetworkRequestFailure(parking: MediatorLiveData<Resource<Parking>>) {
        val offlineData: Parking? = fetchOfflineData()
        if (offlineData != null) {
            parkingMemoryCache.storedParking = offlineData
            parking.value = Resource.error(context.getString(R.string.offline_data), offlineData)
        } else {
            parking.value = Resource.error(context.getString(R.string.network_error), null)
        }
    }

    /**
     * Parse JSON data from file
     */
    private fun fetchOfflineData(): Parking? {
        val fileName = context.getString(R.string.offline_data_file)
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        return gson.fromJson(jsonString, Parking::class.java)

    }

}