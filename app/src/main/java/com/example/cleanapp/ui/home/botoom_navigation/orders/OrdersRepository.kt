package com.example.cleanapp.ui.home.botoom_navigation.orders

import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (ResultHandler<List<Order>>) -> Unit

class OrdersRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getOrders(action: OnLoad) {
        val uid = auth.currentUser!!.uid

        dbRef.child("orders/$uid")
            .get().addOnSuccessListener {
                val map = it.getValue<HashMap<String,Order>>()
                val values = map?.values?.toList()
                action(ResultHandler.Success(values!!))
            }.addOnFailureListener {
                action(ResultHandler.Error(null,it.message!!))
            }
    }
}