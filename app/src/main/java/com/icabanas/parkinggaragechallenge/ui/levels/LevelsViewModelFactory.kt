package com.icabanas.parkinggaragechallenge.ui.levels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import javax.inject.Inject


class LevelsViewModelFactory @Inject constructor(private val parkingRepository: ParkingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LevelsViewModel::class.java)) {
            return LevelsViewModel(parkingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}