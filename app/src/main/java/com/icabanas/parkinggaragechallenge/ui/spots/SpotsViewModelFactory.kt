package com.icabanas.parkinggaragechallenge.ui.spots

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import javax.inject.Inject


class SpotsViewModelFactory @Inject constructor(private val levelsRepository: LevelsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotsViewModel::class.java)) {
            return SpotsViewModel(levelsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}