package com.icabanas.parkinggaragechallenge.ui.levels

import android.arch.lifecycle.*
import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Resource
import javax.inject.Inject

class  LevelsViewModel @Inject constructor(private val parkingRepository: ParkingRepository) : ViewModel() {

    private var _shouldRefresh : MutableLiveData<Boolean> = MutableLiveData()

    var parking: LiveData<Resource<Parking>> = Transformations
            .switchMap(_shouldRefresh) {
                parkingRepository.loadParking()
            }

    fun refreshParking() {
        _shouldRefresh.value = true
    }
}