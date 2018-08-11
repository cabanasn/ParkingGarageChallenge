package com.icabanas.parkinggaragechallenge.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Resource
import javax.inject.Inject

class ParkingViewModel @Inject constructor(val parkingRepository: ParkingRepository): ViewModel() {

    var parking: LiveData<Resource<Parking>> = parkingRepository.parking

    fun initNetworkRequest() = parkingRepository.fetchParking()

}