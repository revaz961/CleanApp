package com.example.cleanapp.ui.home.botoom_navigation.inbox

import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoadChats = (ResultHandler<MutableList<Chat>>) -> Unit
typealias OnLoadMessages = (ResultHandler<List<Message>>) -> Unit

class InboxRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getChats(action: OnLoadChats) {
        val uid = auth.currentUser!!.uid
        val chats = mutableListOf<Chat>()
        var chatCount = 0
        dbRef.child("members").orderByChild(uid).equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatCount = snapshot.childrenCount.toInt()

                    if (chatCount == 0) {
                        action(ResultHandler.Error(null, "you don't have any chat."))
                        return
                    }

                    dbRef.child("members").orderByChild(uid).equalTo(true)
                        .addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {

                                dbRef.child("chats/${snapshot.key}").get()
                                    .addOnSuccessListener { dataSnapshot ->

                                        val chat = dataSnapshot.getValue<Chat>()

                                        chat?.let { chats.add(it) }

                                        if (chats.size == chatCount)
                                            action(ResultHandler.Success(chats))

                                    }.addOnFailureListener {
                                    action(ResultHandler.Error(null, it.message!!))
                                }
                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                            }

                            override fun onChildRemoved(snapshot: DataSnapshot) {}
                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun getMessages(chatId:String,action: OnLoadMessages) {
        val uid = auth.currentUser!!.uid

        dbRef.child("messages/$chatId").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.getValue<HashMap<String,Message>>()
                if (messages != null) {
                    action(ResultHandler.Success(messages.values.toList()))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                action(ResultHandler.Error(null, error.message))
            }
        })
    }
}