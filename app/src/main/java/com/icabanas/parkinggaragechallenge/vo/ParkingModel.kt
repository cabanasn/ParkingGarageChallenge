package com.icabanas.parkinggaragechallenge.vo

import java.util.Date

data class Parking (
        val id: Int,
        val name: String,
        val levels: List<Level>
)

data class Level (
        val id: Int,
        val name: String,
        val spots: List<Spot>
)

data class Spot (
        val id: Int,
        val size: Int,
        val vehicle: Vehicle?
)

data class Vehicle (
        val plate: String,
        val brand: String,
        val color: String,
        val size: Int,
        val arrival: Date
)