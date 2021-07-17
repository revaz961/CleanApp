package com.example.cleanapp.models

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

data class Master(
    @get:PropertyName("img_url")
    @set:PropertyName("img_url")
    var imgUrl: String?,

    @SerializedName("categories")
    val categories: List<MasterCategory>?,

    var recentReviews: List<Review>? = null,

    @SerializedName("rating")
    val rating: Double?,

    @get:PropertyName("n_reviews")
    @set:PropertyName("n_reviews")
    var n_reviews: Int = 0,

    @SerializedName("name")
    val name: String?,

    @get:PropertyName("own_supplements")
    @set:PropertyName("own_supplements")
    var ownSupplements: Boolean = false,

    @get:PropertyName("busy_days")
    @set:PropertyName("busy_days")
    var busyDays:List<Long>? = null,

    @get:PropertyName("cancel_period")
    @set:PropertyName("cancel_period")
    var cancelPeriod:Int? = null,

    var languages:List<String>? = null
)

data class Review(

    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var imageUrl:String? = null,

    @get:PropertyName("at_date")
    @set:PropertyName("at_date")
    var atDate: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("comment")
    val comment: String?,
)

data class MasterCategory(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("price")
    val price: Int = 0,
)