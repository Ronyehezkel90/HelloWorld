package com.ronyehezkel.helloworld

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ronyehezkel.helloworld.model.User

class Firestore private constructor(context: Context) {
    val db = Firebase.firestore

    fun getUser(userEmail: String): Task<DocumentSnapshot> {
        return db.collection("users").document(userEmail).get()

    }

    fun addUser(newUser: User): Task<Void> {
        return db.collection("users").document(newUser.email).set(newUser)
//            .addOnSuccessListener { Log.d("test", "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w("test", "Error writing document", e) }
    }

    fun addParticipant(user: User): Any {
        TODO("Not yet implemented")
    }

    companion object {
        private lateinit var instance: Firestore

        fun getInstance(context: Context): Firestore {
            if (!Companion::instance.isInitialized) {
                instance = Firestore(context)
            }
            return instance
        }
    }

//    fun getUserByEmail(email:String): Task<SignInMethodQueryResult> {
//    }
}