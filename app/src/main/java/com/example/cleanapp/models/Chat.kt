package com.example.cleanapp.models

import com.google.firebase.database.PropertyName

data class Chat(

    @get:PropertyName("at_date")
    @set:PropertyName("at_date")
    var atDate: Long = 0,

    @get:PropertyName("last_message")
    @set:PropertyName("last_message")
    var lastMessage: Message? = null,

    @get:PropertyName("last_message_author")
    @set:PropertyName("last_message_author")
    var lastMessageAuthor: String = "",

    @get:PropertyName("last_message_image")
    @set:PropertyName("last_message_image")
    var lastMessageImage: String = "",

    var receiver:String = "",

    var sender:String = "",

    @get:PropertyName("all_message")
    @set:PropertyName("all_message")
    var allMessages: List<Message>? = null,

    @get:PropertyName("is_read")
    @set:PropertyName("is_read")
    var isRead: Boolean = false
)

data class Message(
    var message: String = "",

    @get:PropertyName("author_id")
    @set:PropertyName("author_id")
    var authorId: String = "",

    @get:PropertyName("at_date")
    @set:PropertyName("at_date")
    var atDate:Long? = null,

    var author: String = "",

    @get:PropertyName("author_image")
    @set:PropertyName("author_image")
    var authorImage: String = ""
)