package com.example.trackmate.ViewModel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.trackmate.R

val tagAr = "NAMASTE"

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(tagAr, "inside onReceive, values recieved: ${intent.getStringExtra("TITLE")}")

        createNotificationChannel(context)
        showNotification(context, title = intent.getStringExtra("TITLE")!!, id = intent.getIntExtra("ID", 0))
    }

    private fun createNotificationChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "ACTIVITY_NOTIFICATION_CHANNEL",
                "Activity Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(context: Context, title: String, id: Int){
        val notification = NotificationCompat.Builder(
            context,
            "ACTIVITY_NOTIFICATION_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("Start activity")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

}