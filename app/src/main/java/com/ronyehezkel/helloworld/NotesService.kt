package com.ronyehezkel.helloworld

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class NotesService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationsManager.getServiceNotification(this)
        startForeground(1, notification)
//        myServiceFunction()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun myServiceFunction() {
        thread(start = true) {
            while (true) {
                Thread.sleep(5000)
                Log.d("test", "5 seconds passed")
            }
        }
    }

}