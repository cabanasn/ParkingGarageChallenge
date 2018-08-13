package com.icabanas.parkinggaragechallenge.ui.spots.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import com.icabanas.parkinggaragechallenge.vo.Spot
import javax.inject.Inject

class SearchSpotViewModel @Inject constructor(val spotsRepository: SpotsRepository) : ViewModel() {

    private val _size: MutableLiveData<Int> = MutableLiveData()

    fun setSize(size: Int) {
        _size.value = size
    }

    var spot: LiveData<Spot> = Transformations
            .switchMap(_size) { input ->
                spotsRepository.findFreeParkingSpot(input)
            }

}