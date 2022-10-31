package com.ronyehezkel.helloworld

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        NotificationsManager.displayDataNotification(this, message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}