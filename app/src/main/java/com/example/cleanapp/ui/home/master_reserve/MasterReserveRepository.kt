package com.example.cleanapp.ui.home.master_reserve

import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.user_data.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*
import javax.inject.Inject

class MasterReserveRepository @Inject constructor(
    private val userData: UserData,
    private val dbRef: DatabaseReference
) {

    fun startChat(master: Master, action: (ResultHandler<Chat>) -> Unit) {
        val user = userData.getUser()
        val firstMember = "${user.uid}_${master.user!!.uid}"
        val message =Message(
            "hi ${master.user!!.name}",
            user.uid!!,
            user.name!!,
            user.imgUrl!!,
            Date().time,
            false
        )

        val chat = Chat(message)

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
                    dbRef.updateChildren(map).addOnCompleteListener {
                        action(ResultHandler.Success(chat))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    action(ResultHandler.Error(null, error.message))
                }

            })
    }
}