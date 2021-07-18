package com.example.cleanapp.models

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

data class Master(
    val uid: String? = null,

    @get:PropertyName("img_url")
    @set:PropertyName("img_url")
    var imgUrl: String? = null,

    @SerializedName("categories")
    val categories: List<MasterCategory>? = null,

    var recentReviews: Review? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @get:PropertyName("n_reviews")
    @set:PropertyName("n_reviews")
    var n_reviews: Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @get:PropertyName("own_supplements")
    @set:PropertyName("own_supplements")
    var ownSupplements: Boolean = false,

    @get:PropertyName("busy_days")
    @set:PropertyName("busy_days")
    var busyDays: List<Long>? = null,

    @get:PropertyName("cancel_period")
    @set:PropertyName("cancel_period")
    var cancelPeriod: Int? = null,

    var languages: List<String>? = null
)

data class Review(
    @get:PropertyName("comments")
    @set:PropertyName("comments")
    var comments: List<Comment>? = null,

    @get:PropertyName("stars")
    @set:PropertyName("stars")
    var stars: List<Float>? = null
)

data class Comment(
    @get:PropertyName("img_url")
    @set:PropertyName("img_url")
    var imageUrl: String? = null,

    @get:PropertyName("date_at")
    @set:PropertyName("date_at")
    var dateAt: Long? = null,

    @get:PropertyName("author")
    @set:PropertyName("author")
    var author: String? = null,

    @get:PropertyName("comment")
    @set:PropertyName("comment")
    var comment: String? = null,
)

data class MasterCategory(
    val category: Int? = null,
    val price: Int = 0,
)