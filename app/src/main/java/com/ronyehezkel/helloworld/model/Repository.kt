package com.ronyehezkel.helloworld.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.FirebaseManager
import com.ronyehezkel.helloworld.SharedPrefManager
import kotlin.concurrent.thread

class Repository private constructor(val applicationContext: Context) {
    private val notesDao = NotesDatabase.getDatabase(applicationContext).getNotesDao()
    private val userDao = NotesDatabase.getDatabase(applicationContext).getUsersDao()
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

    fun getAllNotesAsLiveData(): LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

    fun addNote(note: Note) {
        notesDao.insertNote(note)
        firebaseManager.addNoteToUser(note)
    }

    fun updateNoteImage(note: Note, uri: String, imageType: IMAGE_TYPE) {
        notesDao.updateNoteImage(note, uri, imageType)
    }

    fun addUser(user: User) {
        SharedPrefManager.myUser = user
        thread (start = true){
            userDao.insertUser(user)
        }
    }
}