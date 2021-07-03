package com.example.cleanapp.models


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("city_en")
    val cityEn: String,
    @SerializedName("city_ge")
    val cityGe: String,
    @SerializedName("city_ru")
    val cityRu: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("region")
    val region: String
)