package com.icabanas.parkinggaragechallenge.ui.book

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import javax.inject.Inject


class BookSpotViewModelFactory @Inject constructor(private val spotsRepository: SpotsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookSpotViewModel::class.java)) {
            return BookSpotViewModel(spotsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}