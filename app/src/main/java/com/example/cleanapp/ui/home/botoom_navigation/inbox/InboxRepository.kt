package com.example.cleanapp.ui.home.botoom_navigation.inbox

import com.example.cleanapp.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (MutableList<Chat>) -> Unit

class InboxRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getChats(action: OnLoad) {
        val uid = auth.currentUser!!.uid
        val chats = mutableListOf<Chat>()
        dbRef.child("messages/$uid")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chat = snapshot.getValue<Chat>()
                    chat?.let { chats.add(it) }
                    action(chats)
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun setMasters(master: Master, action: () -> Unit) {
        dbRef.child("masters").push().setValue(master) { error, ref ->
            action()
        }
    }
}