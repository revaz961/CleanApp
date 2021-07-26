package com.example.cleanapp.ui.home.confirmation

import com.example.cleanapp.models.Card
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (Boolean) -> Unit
typealias OnCardsLoad = (List<Card>) -> Unit

class ConfirmationRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun confirmOrder(order: Order, action: OnLoad) {
        val uid = auth.currentUser!!.uid
        dbRef.child("orders/$uid").push().setValue(order)
        //todo send notification
    }

    fun addCard(card: Card, action: OnLoad) {
        val uid = auth.currentUser!!.uid
        val key = dbRef.push().key
        val map = hashMapOf<String, Any>("users/$uid/cards/$key" to card,"masters/$uid/user/cards/$key" to card)
        dbRef.updateChildren(map)
    }


//    fun sendMessage(message: Message, action: OnLoad) {
//        val uid = auth.currentUser!!.uid
//        val key = dbRef.push().key
//        val map = hashMapOf<String, Any>("users/$uid/cards/$key" to card,"masters/$uid/user/cards/$key" to card)
//        dbRef.updateChildren(map)
//        //todo send notification
//    }


    fun getCards(action: OnCardsLoad) {
        val uid = auth.currentUser!!.uid
        dbRef.child("users/$uid/cards").get().addOnSuccessListener {
            val map = it.getValue<HashMap<String,Card>>()
            val values = map!!.values
            action(values.toList())
        }
    }
}