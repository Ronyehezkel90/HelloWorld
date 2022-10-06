package com.ronyehezkel.helloworld

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ronyehezkel.helloworld.model.Note
import com.ronyehezkel.helloworld.model.User

class FirebaseManager private constructor(val context: Context) {
    val db = Firebase.firestore
    val usersCollection = "users"
    companion object{
        private lateinit var instance: FirebaseManager

        fun getInstance(context: Context): FirebaseManager {
            if(!Companion::instance.isInitialized){
                instance = FirebaseManager(context)
            }
            return instance
        }
    }

    fun addUser(user: User): Task<Void> {
        return db.collection(usersCollection).document(user.email).set(user)
    }

    fun getUser(): Task<DocumentSnapshot> {
        return db.collection(usersCollection).document("ronyehezkel90@gmail.com").get()
    }

    fun addNoteToUser(note: Note) {
        val user = SharedPrefManager.myUser
//        user.notes.add(note)
        db.collection(usersCollection).document(SharedPrefManager.myUser.email).set(user)
    }


}