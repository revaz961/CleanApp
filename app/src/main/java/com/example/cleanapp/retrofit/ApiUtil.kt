package com.example.cleanapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtil {

    private const val BASE_URL = "https://fcm.googleapis.com/fcm/send"

    fun apiService(): NotificationApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NotificationApi::class.java)
    }
}