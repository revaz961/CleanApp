package com.example.cleanapp.models

import com.google.firebase.database.PropertyName

data class Chat(
    var name: String = "",

    @get:PropertyName("member_1")
    @set:PropertyName("member_1")
    var member1: UserProfile? = null,

    @get:PropertyName("member_2")
    @set:PropertyName("member_2")
    var member2: UserProfile? = null,

    @get:PropertyName("last_message")
    @set:PropertyName("last_message")
    var lastMessage: Message? = null,

    @get:PropertyName("all_message")
    @set:PropertyName("all_message")
    var allMessages: List<Message>? = null,

    @get:PropertyName("last_message_author")
    @set:PropertyName("last_message_author")
    var lastMessageAuthor: String = "",

    @get:PropertyName("at_date")
    @set:PropertyName("at_date")
    var atDate: Long = 0,

    @get:PropertyName("is_read")
    @set:PropertyName("is_read")
    var isRead: Boolean = false
)

data class Message(
    var message: String = "",

    @get:PropertyName("author_id")
    @set:PropertyName("author_id")
    var authorId: String = "",

    @get:PropertyName("author_name")
    @set:PropertyName("author_name")
    var authorName: String = "",

    @get:PropertyName("author_image")
    @set:PropertyName("author_image")
    var authorImage: String = ""
)