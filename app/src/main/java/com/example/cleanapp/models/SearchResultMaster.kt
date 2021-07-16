package com.example.cleanapp.models

import com.google.gson.annotations.SerializedName

data class SearchResultMaster(
    @SerializedName("img_url")
    val img_url: String?,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("reviews")
    val reviews: List<Comment>?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("n_reviews")
    val n_reviews: Int = 0,
    @SerializedName("name")
    val name: String?,
    @SerializedName("own_supplements")
    val own_supplements: Boolean = false,

)

data class Comment(
    @SerializedName("date")
    val date: String?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("author")
    val author: String?,
)