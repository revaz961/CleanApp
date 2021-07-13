package com.example.cleanapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    var cityId: City? = null,
    var categoryId: Category? = null,
    var date: Long? = null,
    var roomCount: List<RoomCounter>? = null
):Parcelable
