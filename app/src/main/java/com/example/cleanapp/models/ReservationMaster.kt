package com.example.cleanapp.models

import com.google.gson.annotations.SerializedName

data class ReservationMaster(
    @SerializedName("img_url")
    val img_url: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("categories")
    val categories: List<MasterCategory>?,
    @SerializedName("own_supplements")
    val own_supplements: Boolean = false,

    @SerializedName("reviews")
    val reviews: Review?,

    @SerializedName("basic_info")
    val basic_info: BasicInfo?,

    @SerializedName("availability")
    val availability: Availability?,
    )

data class MasterCategory(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("price")
    val price: Int = 0,
)

data class Review(
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("n_reviews")
    val n_reviews: Int = 0,
    @SerializedName("comment")
    val comment: Comment?,
)

data class BasicInfo(
    @SerializedName("languages")
    val languages: List<String>?,
    @SerializedName("response_rate")
    val response_rate: Int = 0
)

data class Availability(
    @SerializedName("timestamp")
    val timestamp: Long?,
    @SerializedName("health")
    val health: String?,
    @SerializedName("cancellation")
    val cancellation: Int = 0
)