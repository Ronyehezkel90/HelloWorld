package com.ronyehezkel.helloworld.fake

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.ronyehezkel.helloworld.IServerManager

class FakeServerManager:IServerManager {
    override fun getUser(userEmail: String): Task<DocumentSnapshot> {
        TODO("Not yet implemented")
    }
}