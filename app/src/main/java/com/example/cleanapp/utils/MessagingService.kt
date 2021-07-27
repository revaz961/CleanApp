package com.example.cleanapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.util.Log.d
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cleanapp.MainActivity
import com.example.cleanapp.R
import com.example.cleanapp.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class MessagingService @Inject constructor(
    private val userRepo: UserRepository,
    @ActivityContext private val context: Context
) : FirebaseMessagingService() {

    companion object {
        private const val REQ_CODE = 1
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        d("FCM", "Message: ${remoteMessage.notification?.body ?: "none"} ")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            d("FCM_RECEIVED", "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()

                sendNotification(remoteMessage)
            }
            // Check if message contains a notification payload.
            if (remoteMessage.notification != null) {
                d("FCM_RECEIVED", "Message Notification Body: ${remoteMessage.notification!!.body}")
            }
        }
    }

    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(context).beginWith(work).enqueue()

    }

    private fun handleNow() {
        d("FCM", "Short lived task is done.")

    }

    private fun sendRegistrationToServer(token: String) {
        userRepo.sendRegistrationToServer(token)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, REQ_CODE, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_send)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(REQ_CODE, notificationBuilder.build())
    }

//    If you are sending Only Notification Payload then the above method works.
//    But if you want to send only Data Payload or both Data and Notification payload then
//    you need to make a few changes:

//    Change this
//          .setContentTitle(remoteMessage.getNotification.getTitle)
//          .setContentText(remoteMessage.getNotification.getBody)
//    To
//          .setContentTitle(remoteMessage.getData.get("your_key1")
//          .setContentText(remoteMessage.getData.get("your_key2")

}
