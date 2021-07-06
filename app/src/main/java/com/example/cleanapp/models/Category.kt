package com.example.cleanapp.models


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_en")
    val category_en: String="",
    @SerializedName("category_ge")
    val category_ge: String="",
    @SerializedName("category_ru")
    val category_ru: String="",
    @SerializedName("color")
    val color: String="",
    @SerializedName("image_url")
    val image_url: String=""
)