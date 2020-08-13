package com.example.otusproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class AlertReceiver : BroadcastReceiver() {
    var title = "title"
    var id = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        title = intent.getStringExtra("TITLE")
        id = intent.getIntExtra("MOVIE_ID", 0)

        val notificationHelper = NotificationHelper(context)
        val nb: NotificationCompat.Builder = notificationHelper.channelNotification(
            title,
            "Don't forget to watch this movie",
            id
        )
        notificationHelper.manager?.notify(1, nb.build())
    }
}