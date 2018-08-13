package com.icabanas.parkinggaragechallenge.ui.spots.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import javax.inject.Inject


class SearchSpotViewModelFactory @Inject constructor(private val spotsRepository: SpotsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchSpotViewModel::class.java)) {
            return SearchSpotViewModel(spotsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}