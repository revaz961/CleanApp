package com.example.cleanapp.models

data class NotificationSent(
    var to: String? = null,
    var notification: Notification? = Notification(),
    var data: Data? = Data()
)

data class Notification(
    var body: String? = "Body of Your Notification",
    var title: String? = "Title of Your Notification"
)

data class Data(
    var body: String? = "Notification Body",
    var title: String? = "Notification Title",
    var key_1: String? = "Value for key_1",
    var key_2: String? = "Value for key_2"
)

