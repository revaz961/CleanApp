package com.example.cleanapp.ui.home.botoom_navigation.orders

import com.example.cleanapp.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (MutableList<Order>) -> Unit

class OrdersRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getOrders(action: OnLoad) {
        val uid = auth.currentUser!!.uid
        val orders = mutableListOf<Order>()
        dbRef.child("orders/$uid")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val order = snapshot.getValue<Order>()
                    order?.let { orders.add(it) }
                    action(orders)
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}