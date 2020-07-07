package com.example.otusproject.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.otusproject.MainActivity
import com.example.otusproject.R
import com.example.otusproject.data.App
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService(){

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(p0: RemoteMessage) {
        for ((key, value) in p0.data.entries) {
            Log.d("mTAG", "key = $key, value = $value")
        }
        sendNotification(p0)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendNotification(p0: RemoteMessage) {
        val  notificationChannelId = getString(R.string.default_notification_channel_id)
        val notificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(notificationChannelId) == null) {
                NotificationChannel(notificationChannelId, "Notifications", NotificationManager.IMPORTANCE_HIGH).apply {
                    shouldShowLights()
                    shouldVibrate()
                    description = "firebase notifications"
                    notificationManager.createNotificationChannel(this)
                }
            }
        }

        val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("MOVIE_ID", p0.data["MOVIE_ID"]?.toInt())

        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_local_movies)
            .setContentTitle(p0.notification?.title)
            .setAutoCancel(true)
            .setContentText("Check out this movie!")
                    .setContentIntent(pendingIntent)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        Log.i("mTag", "Refreshed token: $p0")
    }
}