package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import com.example.cleanapp.models.Comment
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

    fun cancelReservation(order: Order, action: (ResultHandler<Int>) -> Unit) {
        val map =
            hashMapOf<String, Any>(
                "orders/${order.masterUid}/${order.orderId}/status" to OrderStatusEnum.CANCELLED.status,
                "orders/${order.clientUid}/${order.orderId}/status" to OrderStatusEnum.CANCELLED.status
            )
        dbRef.updateChildren(map).addOnSuccessListener {
            action(ResultHandler.Success(OrderStatusEnum.CANCELLED.status))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }

    fun addReview(
        comment: Comment,
        stars: Int,
        master: Master,
        order: Order,
        action: (ResultHandler<Boolean>) -> Unit
    ) {
        comment.commentId = order.orderId
        val map = hashMapOf<String, Any>(
            "reviews/${master.user!!.uid}/comments/${order.orderId}" to comment,
            "reviews/${master.user!!.uid}/stars/${order.orderId}" to stars
        )
        dbRef.updateChildren(map).addOnSuccessListener {
            action(ResultHandler.Success(true))
        }.addOnFailureListener {
            action(ResultHandler.Error(false, it.message!!))
        }
    }

    fun isReviewAdd(masterId: String, orderId: String, action: (ResultHandler<Boolean>) -> Unit) {
        dbRef.child("reviews/$masterId/comments/$orderId")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                        action(ResultHandler.Success(true))
                    else
                        action(ResultHandler.Success(false))
                }

                override fun onCancelled(error: DatabaseError) {

                    action(ResultHandler.Error(null, error.message))
                }

            })
    }
}