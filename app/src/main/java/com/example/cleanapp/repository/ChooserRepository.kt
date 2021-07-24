package com.example.cleanapp.repository

import com.example.cleanapp.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class ChooserRepository @Inject constructor(
    private val dbRef: DatabaseReference,
    private val auth: FirebaseAuth
) {
    fun setOrderInDb(order:Order,action:(Boolean,String) -> Unit){
        dbRef.child("users")
            .child(auth.currentUser!!.uid)
            .child("order")
            .setValue(order) { error, _ ->
                val message = error?.message ?: ""
                val isValid = error != null
                action(isValid,message)
            }
    }
}