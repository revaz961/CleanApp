package com.example.cleanapp.retrofit

import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers(
        "Authorization: key=AAAAO9xKd3I:APA91bGDoKuLqzuurllgPpIt-F0gT1gDrbDBsPv6pBRPnTcSHggb_NacMTrO_pq94li02lAigQGhsd73r0XCVKBwkNClLTqo0Xumfor9jEqis8EYWfe-r873u8KaPklnJ4v1XFX-yC_U",
        "Content-Type: application/json")
    @POST("")
    suspend fun sendNotif(

    )
}