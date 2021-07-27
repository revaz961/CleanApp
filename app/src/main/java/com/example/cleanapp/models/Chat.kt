package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(

    var timestamp: Long = 0,

    @get:PropertyName("last_message")
    @set:PropertyName("last_message")
    var lastMessage: Message? = null,

    @get:PropertyName("sender_image")
    @set:PropertyName("sender_image")
    var senderImage: String = "",

    @get:PropertyName("sender_name")
    @set:PropertyName("sender_name")
    var senderName: String = "",

    @get:PropertyName("sender_id")
    @set:PropertyName("sender_id")
    var senderId: String = "",

    @get:PropertyName("is_read")
    @set:PropertyName("is_read")
    var isRead: Boolean = false,

    @get:PropertyName("chat_id")
    @set:PropertyName("chat_id")
    var chatId:String = ""
) : Parcelable

@Parcelize
data class Message(
    var message: String = "",

    @get:PropertyName("sender_id")
    @set:PropertyName("sender_id")
    var senderId: String = "",

    @get:PropertyName("sender_name")
    @set:PropertyName("sender_name")
    var senderName: String = "",

    @get:PropertyName("sender_image")
    @set:PropertyName("sender_image")
    var senderImage: String = "",

    var timestamp: Long? = null,

    @get:PropertyName("is_read")
    @set:PropertyName("is_read")
    var isRead:Boolean = false
) : Parcelable
