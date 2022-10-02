package com.ronyehezkel.helloworld.model

import android.content.Context
import androidx.lifecycle.LiveData

class Repository private constructor(applicationContext: Context) {
    //    private val notesDao = AppDatabase.getDatabase(applicationContext).getNotesDao()
    private val toDoListDao = AppDatabase.getDatabase(applicationContext).getToDoListDao()

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

    fun getAllToDoLists(): LiveData<List<ToDoList>> {
        return toDoListDao.getAllToDoLists()
    }

    fun addToDoList(toDoList: ToDoList) {
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

//    fun getUsersByToDoList(toDoList: ToDoList): LiveData<List<User>> {
//        return toDoListDao.getToDoListUsers(toDoList.id)
//    }
}