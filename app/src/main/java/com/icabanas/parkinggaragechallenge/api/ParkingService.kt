package com.icabanas.parkinggaragechallenge.api

import com.icabanas.parkinggaragechallenge.vo.Parking
import retrofit2.Call
import retrofit2.http.GET

/**
 * REST API access points
 */
interface ParkingService {

    @GET("5b6e64fb3100001000781982")
    fun getParking(): Call<Parking>

}