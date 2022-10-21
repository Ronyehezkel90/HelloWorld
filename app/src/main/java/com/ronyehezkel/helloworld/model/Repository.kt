package com.ronyehezkel.helloworld.model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.firebase.storage.StorageReference
import com.ronyehezkel.helloworld.FirebaseManager
import kotlin.concurrent.thread

class Repository private constructor(applicationContext: Context) {
    //    private val notesDao = AppDatabase.getDatabase(applicationContext).getNotesDao()
    private val toDoListDao = AppDatabase.getDatabase(applicationContext).getToDoListDao()
    private val firebaseManager = FirebaseManager.getInstance(applicationContext)

    companion object {
        private lateinit var instance: Repository

        fun getInstance(context: Context): Repository {
            if (!Companion::instance.isInitialized) {
                instance = Repository(context)
            }
            return instance
        }
    }

    fun addUserToToDoList(toDoList: ToDoList, user: User) {
        toDoList.participants.usersList.add(user)
        toDoListDao.updateParticipantsList(toDoList.title, toDoList.participants)
    }

    fun addNote(toDoList: ToDoList, note: Note) {
        toDoList.notes.notesList.add(note)
        toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
    }

    fun updateNoteImage(toDoList: ToDoList) {
        toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
    }

    fun getPartOfToDoLists(countLetters: Int) {
        firebaseManager.getPartOfToDoLists(countLetters).addOnSuccessListener {
            print(it.documents)
            for (doc in it.documents){
                if(doc.data!=null){
                    val toDoList = doc.toObject(ToDoList::class.java)
                    thread (start = true){
                        toDoListDao.insertToDoList(toDoList!!)
                    }
                }
            }
        }
    }

    fun getLocalToDoLists(): LiveData<List<ToDoList>> {
//        firebaseManager.getAllToDoLists().addOnSuccessListener {
//            print(it.documents)
//            for (doc in it.documents){
//                if(doc.data!=null){
//                    val toDoList = doc.toObject(ToDoList::class.java)
//                    thread (start = true){
//                        toDoListDao.insertToDoList(toDoList!!)
//                    }
//                }
//            }
//        }.addOnFailureListener {
//
//        }
        return toDoListDao.getAllToDoLists()
    }

    fun addToDoList(toDoList: ToDoList) {
        firebaseManager.updateToDoList(toDoList)
        return toDoListDao.insertToDoList(toDoList)
    }

    fun getNotesByToDoList(toDoList: ToDoList): LiveData<NotesList> {
        return toDoListDao.getAllNotes(toDoList.title)
    }

    fun getToDoListByTitle(toDoListTitle: String): ToDoList {
        return toDoListDao.getToDoListByTitle(toDoListTitle)
    }

    fun getUsersByToDoList(toDoList: ToDoList): LiveData<Participants> {
        return toDoListDao.getAllUsers(toDoList.title)
    }

    fun addProfileImageForUser(uri: Uri) {
        firebaseManager.uploadUserProfileImage(uri)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun getProfilePhotoPath(): StorageReference {
        return firebaseManager.getUserProfileImageReference()
    }

//    fun getUsersByToDoList(toDoList: ToDoList): LiveData<List<User>> {
//        return toDoListDao.getToDoListUsers(toDoList.id)
//    }
}