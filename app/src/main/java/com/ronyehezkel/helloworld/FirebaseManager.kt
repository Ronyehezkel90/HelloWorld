package com.ronyehezkel.helloworld

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ronyehezkel.helloworld.model.SpManager
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.model.User
import kotlin.concurrent.thread


class FirebaseManager private constructor(context: Context) {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    val spManager = SpManager.getInstance(context)

    private fun getUserEmail(): String {
        return spManager.getMyUser().email
    }

    fun getUser(userEmail: String = spManager.getMyUser().email): Task<DocumentSnapshot> {
        return db.collection("users").document(userEmail).get()
    }

    fun updateUser(newUser: User): Task<Void> {
        return db.collection("users").document(newUser.email).set(newUser)
    }

    fun updateToDoList(toDoList: ToDoList): Task<Void> {
        val user = spManager.getMyUser()
        user.toDoLists.add(getUserEmail() + "_" + toDoList.title)
        return updateUser(user).addOnSuccessListener {
            db.collection("ToDoList").document(getUserEmail() + "_" + toDoList.title)
                .set(toDoList)
        }
    }

    fun getUserProfile(): Task<ByteArray> {
        return storage.reference.child(spManager.getMyUser().email).getBytes(Long.MAX_VALUE)
    }

    fun uploadUserProfile(imagePath: Uri): UploadTask {
        return storage.reference.child(spManager.getMyUser().email).putFile(imagePath)
    }

    fun getUserProfileRef(): StorageReference {
        return storage.reference.child(spManager.getMyUser().email)
    }

    fun getAllToDoLists(): Task<DocumentSnapshot> {
        return getUser(spManager.getMyUser().email).addOnSuccessListener { userJson ->
            if (userJson.data != null) {
                val user = userJson.toObject(User::class.java)
                for (toDoList in user!!.toDoLists) {
                    db.collection("ToDoList").document(toDoList).get()
                        .addOnSuccessListener { toDoListJson ->
                            if (toDoListJson.data != null) {
                                val toDoList = toDoListJson.toObject(ToDoList::class.java)
                            }
                        }
                }
            }
        }
    }

    fun getToDoList(toDoList: String): Task<DocumentSnapshot> {
        return db.collection("ToDoList").document(toDoList).get()
    }

    fun getToDoListPagination(): Task<QuerySnapshot> {
        return db.collection("ToDoList")
            .orderBy("timestamp")
            .endAt(1666199133631).get()
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

}