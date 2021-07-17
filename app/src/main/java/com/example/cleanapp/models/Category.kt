package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET

@Parcelize
data class Category(
    @SerializedName("category_en")
    @get:PropertyName("category_en")
    @set:PropertyName("category_en")
    var categoryEn: String="",
    @SerializedName("category_ge")
    @get:PropertyName("category_ge")
    @set:PropertyName("category_ge")
    var categoryGe: String="",
    @SerializedName("category_ru")
    @get:PropertyName("category_ru")
    @set:PropertyName("category_ru")
    var categoryRu: String="",
    @SerializedName("color")
    @get:PropertyName("color")
    @set:PropertyName("color")
    var color: String="",
    @SerializedName("image_url")
    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var imageUrl: String=""
):Parcelable