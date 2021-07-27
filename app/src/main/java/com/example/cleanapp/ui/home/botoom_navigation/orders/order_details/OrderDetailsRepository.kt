package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class OrderDetailsRepository @Inject constructor(
    private val dbRef: DatabaseReference
) {
    fun getMaster(uid: String, action: (ResultHandler<Master>) -> Unit) {
        dbRef.child("masters/$uid").get().addOnSuccessListener {
            val master = it.getValue<Master>()!!
            action(ResultHandler.Success(master))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }

    fun cancelReservation(order: Order) {
        dbRef.child("orders/${order.masterUid}/${order.orderId}/status")
            .setValue(OrderStatusEnum.CANCELLED.status).addOnSuccessListener {
                //todo send notification to master
            }
    }
}