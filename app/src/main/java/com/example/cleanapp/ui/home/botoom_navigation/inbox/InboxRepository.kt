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
typealias OnLoad = (ResultHandler<String>) -> Unit

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

                                        if (chats.size <= chatCount)
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


    fun getMessages(
        chatId: String,
        onMessageAdd: (ResultHandler<Message>) -> Unit,
        onLoadMessage: OnLoadMessages
        ) {
        val messages = mutableListOf<Message>()
        var messageCount = 0
        dbRef.child("messages/$chatId")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageCount = snapshot.childrenCount.toInt()

                    if (messageCount == 0) {
                        onLoadMessage(ResultHandler.Error(null, "you don't have any messages."))
                        return
                    }

                    dbRef.child("messages/$chatId")
                        .addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                val message = snapshot.getValue<Message>()
                                if (messageCount >= messages.size) {
                                    message?.let {
                                        messages.add(it)
                                        onLoadMessage(ResultHandler.Success(messages))
                                    }
                                } else {
                                    message?.let {
                                        onMessageAdd(ResultHandler.Success(it))
                                    }
                                }
                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {}

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
                    onLoadMessage(ResultHandler.Error(null, error.message))
                }
            })
    }


    fun sendMessage(message: Message, chatId: String, action: OnLoad) {
        val newKey = dbRef.push().key
        message.messageId = newKey

        val map = mutableMapOf<String, Any>(
            "chats/$chatId/last_message" to message,
            "messages/$chatId/$newKey" to message
        )

        dbRef.updateChildren(map).addOnSuccessListener {

            action(ResultHandler.Success("Message send"))

        }.addOnFailureListener {

            action(ResultHandler.Error(null, it.message!!))
        }
    }

    fun checkReadChat(chat: Chat, action: (ResultHandler<Chat>) -> Unit) {

        val map = hashMapOf<String, Any>(
            "chats/${chat.chatId}/last_message/is_read" to true,
            "messages/${chat.chatId}/${chat.lastMessage?.messageId}/is_read" to true
        )
        dbRef.updateChildren(map).addOnSuccessListener {
            action(ResultHandler.Success(chat))
        }.addOnFailureListener {
            action(ResultHandler.Error(chat, it.message!!))
        }
    }

    fun checkReadMessage(chat: Chat, message: Message) {

        val map = hashMapOf<String, Any>(
            "messages/${chat.chatId}/${message.messageId}/is_read" to true
        )

        if (chat.lastMessage!!.messageId == message.messageId)
            map["chats/${chat.chatId}/last_message/is_read"] = true

        dbRef.updateChildren(map)
    }
}