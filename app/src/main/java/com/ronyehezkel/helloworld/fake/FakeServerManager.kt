package com.ronyehezkel.helloworld.fake

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.ronyehezkel.helloworld.ServerManager

class FakeServerManager:ServerManager {
    override fun getUser(userEmail: String): Task<DocumentSnapshot> {
        TODO("Not yet implemented")
    }
}