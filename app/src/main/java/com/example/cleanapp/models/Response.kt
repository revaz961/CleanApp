package com.example.cleanapp.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("cities")
    val cities: List<City>
)