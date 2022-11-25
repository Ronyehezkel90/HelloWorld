package com.ronyehezkel.helloworld

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import com.ronyehezkel.helloworld.model.Note
import com.ronyehezkel.helloworld.ui.comp.CompRegistrationActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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

    fun displayDataNotification(context: Context, message: RemoteMessage) {
        createNotificationChannel(context)
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setSmallIcon(android.R.drawable.ic_menu_call)
                .setContentText(message.data["body"])

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, builder.build())
    }

    fun displayPushNotification(context: Context, message: RemoteMessage) {
        createNotificationChannel(context)
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(message.notification!!.title)
                .setSmallIcon(android.R.drawable.ic_menu_call)
                .setContentText(message.notification!!.body)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, builder.build())
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
            Intent(context, CompRegistrationActivity::class.java),
            0
        )
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My service notification")
            .setSmallIcon(R.drawable.camera)
            .setContentIntent(pendingIntent)
            .setContentText("Now the user can see that im working in background").build()
    }

    fun sendMessage(title: String, content: String, userToken: String) {
        val data = hashMapOf<String, Any>(Pair("title", title), Pair("content", content))
        val body = hashMapOf<String, Any>(Pair("data", data), Pair("to", userToken))
//        val headers = hashMapOf(Pair("Content-Type", "application/json"), Pair("authorization", "key=AAAAk0goG_0:APA91bG1Bpz-rWF7DYn5c_CgWjAQgiD1RKTfC9zBpycSUyboWRlYBi52VOUncDdK0-VoegXq0SMH8LGNF6SZwxj8Sdob-IkcBckifsyLenVzXp5x3T_c7mw0dKXUyrutANgT_cuFPx4I"))

        NotificationsApiInterface.create().sendNotification(body).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                print(response)
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                print(t)
            }

        })
    }

    fun sendMessager(title: String, content: String, userToken: String) {
        val endpoint = "https://fcm.googleapis.com/fcm/send"
        try {
            val url = URL(endpoint)
            val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.readTimeout = 10000
            httpsURLConnection.connectTimeout = 15000
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = true
            //todo:// remove this
            val serverKey = "{Server Key}"
            // Adding the necessary headers
            httpsURLConnection.setRequestProperty("authorization", "key=$serverKey")
            httpsURLConnection.setRequestProperty("Content-Type", "application/json")

            // Creating the JSON with post params
            val body = JSONObject()

            val data = JSONObject()
            data.put("title", title)
            data.put("content", content)
            body.put("data", data)

//            body.put("to", "/topics/$topic")
            body.put("to", userToken)

            val outputStream: OutputStream = BufferedOutputStream(httpsURLConnection.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
            writer.write(body.toString())
            writer.flush()
            writer.close()
            outputStream.close()
            val responseCode: Int = httpsURLConnection.responseCode
            val responseMessage: String = httpsURLConnection.responseMessage
            Log.d("Response:", "$responseCode $responseMessage")
            var result = String()
            var inputStream: InputStream? = null
            inputStream = if (responseCode in 400..499) {
                httpsURLConnection.errorStream
            } else {
                httpsURLConnection.inputStream
            }

            if (responseCode == 200) {
                Log.e("Success:", "notification sent $title \n $content")
                // The details of the user can be obtained from the result variable in JSON format
            } else {
                Log.e("Error", "Error Response")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}