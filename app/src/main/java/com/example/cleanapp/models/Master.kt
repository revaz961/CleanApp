package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Master(
    var user: User? = null,
    var categories: List<MasterCategory>? = null,

    var city:City? = null,

    var reviews: Review? = null,

    @get:PropertyName("last_comment")
    @set:PropertyName("last_comment")
    var lastComments: List<Comment>? = null,

    val rating: Float? = null,

    @get:PropertyName("n_reviews")
    @set:PropertyName("n_reviews")
    var nReviews: Int = 0,

    @get:PropertyName("own_supplements")
    @set:PropertyName("own_supplements")
    var ownSupplements: Boolean = false,

    @get:PropertyName("busy_days")
    @set:PropertyName("busy_days")
    var busyDays: List<Long>? = null,

    @get:PropertyName("cancel_period")
    @set:PropertyName("cancel_period")
    var cancelPeriod: Int? = 1,

    var languages: List<String>? = null

) : Parcelable

@Parcelize
data class Review(
    @get:PropertyName("comments")
    @set:PropertyName("comments")
    var comments: List<Comment>? = null,

    @get:PropertyName("stars")
    @set:PropertyName("stars")
    var stars: List<Float>? = null,

    @get:PropertyName("review_id")
    @set:PropertyName("review_id")
    var reviewId: String? = null
) : Parcelable

@Parcelize
data class Comment(
    @get:PropertyName("comment")
    @set:PropertyName("comment")
    var comment: String? = null,

    @get:PropertyName("author")
    @set:PropertyName("author")
    var author: String? = null,

    @get:PropertyName("img_url")
    @set:PropertyName("img_url")
    var imageUrl: String? = null,

    @get:PropertyName("date_at")
    @set:PropertyName("date_at")
    var dateAt: Long? = null,

    @get:PropertyName("comment_id")
    @set:PropertyName("comment_id")
    var commentId: String? = null

) : Parcelable

@Parcelize
data class MasterCategory(
    val category: Category? = null,
    var price: Float = 0f,
) : Parcelable