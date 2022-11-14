package com.ronyehezkel.helloworld

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface ServerManager {
    fun getUser(userEmail: String): Task<DocumentSnapshot>
}