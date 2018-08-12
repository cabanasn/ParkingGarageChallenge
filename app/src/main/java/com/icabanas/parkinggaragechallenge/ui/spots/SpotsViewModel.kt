package com.icabanas.parkinggaragechallenge.ui.spots

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.icabanas.parkinggaragechallenge.repository.LevelsRepository
import com.icabanas.parkinggaragechallenge.vo.Level
import javax.inject.Inject

class SpotsViewModel @Inject constructor(levelsRepository: LevelsRepository) : ViewModel() {

    private val _levelId: MutableLiveData<Int> = MutableLiveData()
    val levelId: LiveData<Int>
        get() = _levelId

    fun setId(id: Int) {
        _levelId.value = id
    }

    var level: LiveData<Level> = Transformations
            .switchMap(_levelId) { input ->
                levelsRepository.loadLevel(input)
            }

}