package com.icabanas.parkinggaragechallenge.ui.spots.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import javax.inject.Inject


class SpotDetailViewModelFactory @Inject constructor(private val spotsRepository: SpotsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotDetailViewModel::class.java)) {
            return SpotDetailViewModel(spotsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}