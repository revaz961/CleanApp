package com.example.cleanapp.models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("city_en")
    val city_en: String="",
    @SerializedName("city_ge")
    val city_ge: String="",
    @SerializedName("city_ru")
    val city_ru: String="",
    @SerializedName("lat")
    val lat: String="",
    @SerializedName("lng")
    val lng: String="",
    @SerializedName("region")
    val region: String=""
)