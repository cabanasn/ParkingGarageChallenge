package com.icabanas.parkinggaragechallenge.ui.levels

import com.icabanas.parkinggaragechallenge.vo.Level

class LevelBindItem(val level: Level) {
    var title: String = level.name
    var bookedText: String = "${bookedSpots()}"
    var emptyText: String = "/${level.spots.size}"
    var bookedWeight: Float = bookedSpots().toFloat()
    var emptyWeight: Float = level.spots.size.toFloat() - bookedSpots().toFloat()

    fun bookedSpots(): Int = level.spots.filter { it.vehicle != null }.size
}