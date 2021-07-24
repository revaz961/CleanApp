package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.utils.OrderStatusEnum
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class OrderDetailsRepository @Inject constructor(
    private val dbRef: DatabaseReference
) {
    fun getMaster(uid: String, action: (Master) -> Unit) {
        dbRef.child("master/$uid").get().addOnSuccessListener {
            action(it.getValue<Master>()!!)
        }
    }

    fun cancelReservation(order: Order) {
        dbRef.child("orders/${order.masterUid}/${order.orderId}/status")
            .setValue(OrderStatusEnum.CANCELLED.status).addOnSuccessListener {
                //todo send notification to master
            }
    }
}