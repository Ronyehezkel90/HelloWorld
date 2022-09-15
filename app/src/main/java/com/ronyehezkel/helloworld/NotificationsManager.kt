package com.ronyehezkel.helloworld

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

object NotificationsManager {
    val CHANNEL_ID = "CHANNEL_ID"

    fun createNotificationChannel(context: Context) {
        val name = "Notification Channel"
        val descriptionText = "Notification Chanell"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = descriptionText
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun displayNewNoteNotification(context: Context, note: Note) {
        createNotificationChannel(context)
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("New Note")
                .setSmallIcon(R.drawable.camera)
                .setContentText("Hey! Note -${note.title}- has been added to your list")

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, builder.build())
    }

    fun getServiceNotification(context: Context): Notification {
        createNotificationChannel(context)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, RegistrationActivity::class.java),
            0
        )
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My service notification")
            .setSmallIcon(R.drawable.camera)
            .setContentIntent(pendingIntent)
            .setContentText("Now the user can see that im working in background").build()
    }

}