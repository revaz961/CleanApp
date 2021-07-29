package com.example.cleanapp.models

import android.os.Parcelable
import com.example.cleanapp.extensions.minuteToHoursFloat
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(

    @get:PropertyName("city_id")
    @set:PropertyName("city_id")
    var city: City? = null,

    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var category: Category? = null,

    @get:PropertyName("date")
    @set:PropertyName("date")
    var date: Long? = null,

    @get:PropertyName("room_count")
    @set:PropertyName("room_count")
    var roomCount: List<RoomCounter>? = null,

    @get:PropertyName("price")
    @set:PropertyName("price")
    var price: Float = 0.00f,

    @get:PropertyName("status")
    @set:PropertyName("status")
    var status: Int = 0,

    @get:PropertyName("master_uid")
    @set:PropertyName("master_uid")
    var masterUid: String? = null,

    @get:PropertyName("address")
    @set:PropertyName("address")
    var address: String? = null,

    @get:PropertyName("client_uid")
    @set:PropertyName("client_uid")
    var clientUid: String? = null,

    @get:PropertyName("duration")
    @set:PropertyName("duration")
    var duration: Int? = null,

    @get:PropertyName("order_id")
    @set:PropertyName("order_id")
    var orderId: String? = null,

    @get:PropertyName("reservation_date")
    @set:PropertyName("reservation_date")
    var reservationDate: Long? = null

) : Parcelable {

     fun getCleaningPrice(): Float {
        return if (duration != null)
            duration!!.minuteToHoursFloat() * price
        else
            price
    }


     fun getServiceFee(): Float {
        return getCleaningPrice() * 0.18f
    }

     fun getTotalPrice(): Float {
        return getCleaningPrice() + getServiceFee()
    }
}