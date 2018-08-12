package com.icabanas.parkinggaragechallenge.ui.levels

import com.icabanas.parkinggaragechallenge.vo.Level

class LevelBindItem (val level: Level) {
    var title: String = level.name
    var subtitle: String = "${bookedSpots()}/${level.spots.size}"
    var bookedWeight: Int = bookedSpots()
    var emptyWeight: Int = level.spots.size

    fun bookedSpots(): Int = level.spots.filter { it.vehicle != null }.size
}