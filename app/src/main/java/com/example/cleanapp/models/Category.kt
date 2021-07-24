package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @get:PropertyName("category_en")
    @set:PropertyName("category_en")
    var categoryEn: String="",

    @get:PropertyName("category_ge")
    @set:PropertyName("category_ge")
    var categoryGe: String="",

    @get:PropertyName("category_ru")
    @set:PropertyName("category_ru")
    var categoryRu: String="",

    @get:PropertyName("color")
    @set:PropertyName("color")
    var color: String="",

    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var imageUrl: String="",

    @Exclude
    var isChecked:Boolean = false
):Parcelable