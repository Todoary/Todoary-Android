package com.uni.todoary.util

import com.google.firebase.messaging.FirebaseMessagingService
import android.R
import android.app.Notification
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage


class TodoaryFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) { // 새로운 토큰이 발급되면 로그에 찍힘
        Log.d("FCM Log", "Refreshed token: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationManager = NotificationManagerCompat.from(
            applicationContext
        )
        val CHANNEL_ID = "default_channel_id"
        val CHANNEL_NAME = "default_channel_name"
        var builder: NotificationCompat.Builder? = null
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(applicationContext)
        }
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        builder.setContentTitle(title)
            .setContentText(body)
            // Icon 설정
            .setSmallIcon(R.drawable.ic_input_delete)
        val notification: Notification = builder.build()
        notificationManager.notify(1, notification)
    }
}