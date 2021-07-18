package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    @get:PropertyName("city_id")
    @set:PropertyName("city_id")
    var cityId: City? = null,

    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var categoryId: Category? = null,

    @get:PropertyName("date")
    @set:PropertyName("date")
    var date: Long? = null,

    @get:PropertyName("room_count")
    @set:PropertyName("room_count")
    var roomCount: List<RoomCounter>? = null
):Parcelable