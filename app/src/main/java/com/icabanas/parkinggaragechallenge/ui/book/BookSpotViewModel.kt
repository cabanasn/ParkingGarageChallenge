package com.icabanas.parkinggaragechallenge.ui.book

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import com.icabanas.parkinggaragechallenge.vo.Level
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import java.util.*
import javax.inject.Inject

class BookSpotViewModel @Inject constructor(val spotsRepository: SpotsRepository) : ViewModel() {

    data class SpotLevelId ( val levelId: Int, val spotId: Int )

    private val _spotLevelId: MutableLiveData<SpotLevelId> = MutableLiveData()
    val bookSpotResult: MutableLiveData<Boolean> = MutableLiveData()

    fun setIds(levelId: Int, spotId: Int) {
        _spotLevelId.value = SpotLevelId(levelId, spotId)
    }

    fun bookSpot(vehicle: Vehicle) {
        val spotId = _spotLevelId.value?.spotId
        val levelId = _spotLevelId.value?.levelId
        if (spotId != null && levelId != null) {
            bookSpotResult.value = spotsRepository.bookSpot(levelId, spotId, vehicle)
        }
    }

    var spot: LiveData<Spot> = Transformations
            .switchMap(_spotLevelId) { input ->
                spotsRepository.loadSpot(input.levelId, input.spotId)
            }

}