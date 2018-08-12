package com.icabanas.parkinggaragechallenge.ui.spots.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.repository.SpotsRepository
import com.icabanas.parkinggaragechallenge.vo.Level
import com.icabanas.parkinggaragechallenge.vo.Spot
import javax.inject.Inject

class SpotDetailViewModel @Inject constructor(val spotsRepository: SpotsRepository) : ViewModel() {

    data class SpotLevelId ( val levelId: Int, val spotId: Int )

    private val _spotLevelId: MutableLiveData<SpotLevelId> = MutableLiveData()
    val releaseSpotResult: MutableLiveData<Boolean> = MutableLiveData()

    fun setIds(levelId: Int, spotId: Int) {
        _spotLevelId.value = SpotLevelId(levelId, spotId)
    }

    fun releaseSpot() {
        val spotId = _spotLevelId.value?.spotId
        val levelId = _spotLevelId.value?.levelId
        if (spotId != null && levelId != null) {
            releaseSpotResult.value = spotsRepository.releaseSpot(levelId, spotId)
        }
    }

    var spot: LiveData<Spot> = Transformations
            .switchMap(_spotLevelId) { input ->
                spotsRepository.loadSpot(input.levelId, input.spotId)
            }



}