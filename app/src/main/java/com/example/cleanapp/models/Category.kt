package com.example.cleanapp.models


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_en")
    val categoryEn: String,
    @SerializedName("category_ge")
    val categoryGe: String,
    @SerializedName("category_ru")
    val categoryRu: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("image_url")
    val imageUrl: String
)