package com.icabanas.parkinggaragechallenge.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import javax.inject.Inject


class ParkingViewModelFactory @Inject constructor(val parkingRepository: ParkingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParkingViewModel::class.java)) {
            return ParkingViewModel(parkingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}