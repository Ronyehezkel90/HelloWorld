package com.ronyehezkel.helloworld

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val senderName = message.data["senderName"]
        val content = message.data["content"]
        val todoListName = message.data["todoListName"]
        NotificationsManager.displayDataMessageNotification(
            this,
            "$senderName added you to $todoListName",
            content!!
        )
    }
}