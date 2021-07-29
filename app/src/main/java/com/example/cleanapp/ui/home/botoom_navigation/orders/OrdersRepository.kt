package com.example.cleanapp.ui.home.botoom_navigation.orders

import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = snapshot.getValue<HashMap<String, Order>>()

                    map?.values?.toList()?.let {
                        action(ResultHandler.Success(it))
                    } ?: action(ResultHandler.Error(null,"You don't have any order"))
                }

                override fun onCancelled(error: DatabaseError) {
                    action(ResultHandler.Error(null, error.message))
                }
            })
    }
}