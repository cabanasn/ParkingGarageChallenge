package com.icabanas.parkinggaragechallenge.ui

import android.arch.lifecycle.ViewModel
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import javax.inject.Inject

class ParkingViewModel @Inject constructor(val parkingRepository: ParkingRepository): ViewModel() {

    fun getParking() = parkingRepository.getParking()
}