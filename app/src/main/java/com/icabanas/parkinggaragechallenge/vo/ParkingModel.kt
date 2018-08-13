package com.icabanas.parkinggaragechallenge.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Parking (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("levels")
        val levels: List<Level>
)

data class Level (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("spots")
        val spots: List<Spot>
)

data class Spot (
        @SerializedName("id")
        val id: Int,
        @SerializedName("size")
        val size: Int,
        @SerializedName("vehicle")
        var vehicle: Vehicle?
) {
        @Expose(serialize = false)
        var levelId: Int = 0
        @Expose(serialize = false)
        var levelName: String = ""
}

data class Vehicle (
        @SerializedName("plate")
        val plate: String,
        @SerializedName("brand")
        val brand: String,
        @SerializedName("color")
        val color: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("arrival")
        val arrival: Date
)