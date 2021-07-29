package com.example.cleanapp.ui.home.confirmation

import com.example.cleanapp.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (ResultHandler<Boolean>) -> Unit
typealias OnCardsLoad = (ResultHandler<List<Card>>) -> Unit
typealias onOrderLoad = (ResultHandler<Order>) -> Unit

class ConfirmationRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun confirmOrder(order: Order, action: onOrderLoad) {
        val key = dbRef.push().key!!
        order.orderId = key
        val map = hashMapOf<String,Any>(
            "orders/${order.clientUid}/$key" to order,
            "orders/${order.masterUid}/$key" to order
        )
        dbRef.updateChildren(map).addOnSuccessListener {
            action(ResultHandler.Success(order))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
        //todo send notification
    }

    fun addCard(card: Card, action: OnLoad) {
        val uid = auth.currentUser!!.uid
        dbRef.child("cards/$uid").push().setValue(card).addOnSuccessListener {
            action(ResultHandler.Success(true))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }


    fun sendMessage(message: Message, chat: Chat, firstMember: String, action: OnLoad) {

        dbRef.child("members").orderByChild(firstMember).equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = mutableMapOf<String, Any>()
                    val newKey = dbRef.push().key
                    message.messageId = newKey
                    if (snapshot.exists()) {
                        val key = snapshot.children.first { it.exists() }.key
                        chat.chatId = key!!
                        map["chats/$key"] = chat
                        map["messages/$key/$newKey"] = message
                    } else {
                        chat.chatId = newKey!!
                        val usersId = firstMember.split('_')
                        val secondMember = usersId.reversed().joinToString("_")
                        map["chats/$newKey"] = chat
                        map["messages/$newKey/$newKey"] = message
                        map["members/$newKey/$firstMember"] = true
                        map["members/$newKey/$secondMember"] = true
                        map["members/$newKey/${usersId[0]}"] = true
                        map["members/$newKey/${usersId[1]}"] = true
                    }
                    dbRef.updateChildren(map)
                }

                override fun onCancelled(error: DatabaseError) {
                    action(ResultHandler.Error(null, error.message!!))
                }

            })
    }


    fun getCards(action: OnCardsLoad) {
        val uid = auth.currentUser!!.uid
        dbRef.child("cards/$uid").get().addOnSuccessListener { dataSnapshot ->
            val map = dataSnapshot.getValue<HashMap<String, Card>>()

            map?.let {
                action(ResultHandler.Success(it.values.toList()))
            }

        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }
}