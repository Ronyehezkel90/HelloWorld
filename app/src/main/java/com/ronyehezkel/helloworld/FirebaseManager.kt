package com.ronyehezkel.helloworld

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ronyehezkel.helloworld.model.SpManager
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.model.User

class FirebaseManager private constructor(context: Context):ServerManager {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    val fcm = FirebaseMessaging.getInstance()

    override fun getUser(userEmail: String): Task<DocumentSnapshot> {
        return db.collection("users").document(userEmail).get()
    }

    fun getUserProfileImageReference(): StorageReference {
        return storage.reference.child("shtut")
    }

    fun uploadUserProfileImage(uri: Uri): UploadTask {
        return getUserProfileImageReference().putFile(uri)
    }

    fun updateUser(newUser: User): Task<Void> {
        return db.collection("users").document(newUser.email).set(newUser)
//            .addOnSuccessListener { Log.d("test", "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w("test", "Error writing document", e) }
    }

    fun updateToDoList(toDoList: ToDoList): Task<Void> {
        return db.collection("ToDoList").document(toDoList.id).set(toDoList)
    }

    fun getPartOfToDoLists(countLetters: Int): Task<QuerySnapshot> {
        val letterslist = arrayListOf("a", "b", "c", "d", "e", "f", "g", "h", "i")
        return db.collection("ToDoList").orderBy("title")
            .startAt(letterslist.get(countLetters))
            .endAt(letterslist.get(countLetters + 2)).get()
    }

    fun getAllToDoLists(): Task<QuerySnapshot> {
        return db.collection("ToDoList").get()
    }

    companion object {
        private lateinit var instance: FirebaseManager

        fun getInstance(context: Context): FirebaseManager {
            if (!Companion::instance.isInitialized) {
                instance = FirebaseManager(context)
            }
            return instance
        }
    }

    fun getFcmToken(): Task<String> {
        return fcm.token
    }

    fun subscribeToTopic(){
//        FirebaseMessaging.getInstance().subscribeToTopic(R.string.topic.toString());
    }

//    fun getUserByEmail(email:String): Task<SignInMethodQueryResult> {
//    }
}