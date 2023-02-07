package com.uni.todoary.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uni.todoary.R
import com.uni.todoary.feature.main.ui.view.MainActivity
import com.uni.todoary.feature.setting.ui.view.AlarmActivity

class MessagingService : FirebaseMessagingService() {

    // 메시지 수신하는 메서드
    override fun onMessageReceived(remoteMsg: RemoteMessage) {
        if (remoteMsg.data.isEmpty()){
            sendNotification(
                remoteMsg.data["title"].toString(),
                remoteMsg.data["body"].toString()
            )
        }else{
            remoteMsg.notification?.let{
                sendNotification(
                    remoteMsg.notification!!.title.toString(),
                    remoteMsg.notification!!.body.toString()
                )
            }
        }
    }

    // 알림 생성 메서드
    private fun sendNotification(title: String, body: String) {
        val notifyId = (System.currentTimeMillis()/7).toInt()

        val intent = Intent(this, MainActivity::class.java )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, notifyId, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.firebase_notification_channel_channel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notifyId, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d("onNewToken","Success save Token")
        super.onNewToken(token) // Token을 서버로 전송
    }
    private fun showNotication(title: String?, body: String) {

    }
}