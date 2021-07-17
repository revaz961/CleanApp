package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    @SerializedName("city_en")
    @get:PropertyName("city_en")
    @set:PropertyName("city_en")
    var cityEn: String="",
    @SerializedName("city_ge")
    @get:PropertyName("city_ge")
    @set:PropertyName("city_ge")
    var cityGe: String="",
    @SerializedName("city_ru")
    @get:PropertyName("city_ru")
    @set:PropertyName("city_ru")
    var cityRu: String="",
    @SerializedName("lat")
    val lat: String="",
    @SerializedName("lng")
    val lng: String="",
    @SerializedName("region")
    val region: String=""
):Parcelable