package com.example.cleanapp

import android.os.Bundle
import android.util.Log.d
import android.util.Log.w
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanapp.utils.MessagingService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            d("FCM", token.toString())
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        init()
    }

    private fun init(){

    }
}