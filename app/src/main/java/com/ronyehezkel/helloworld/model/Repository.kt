package com.ronyehezkel.helloworld.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.FirebaseManager
import kotlinx.coroutines.CoroutineScope
import kotlin.concurrent.thread

class Repository private constructor(applicationContext: Context) {
    //    private val notesDao = AppDatabase.getDatabase(applicationContext).getNotesDao()
    private val toDoListDao = AppDatabase.getDatabase(applicationContext).getToDoListDao()
    val firebaseManager = FirebaseManager.getInstance(applicationContext)

    companion object {
        private lateinit var instance: Repository

        fun getInstance(context: Context): Repository {
            if (!Companion::instance.isInitialized) {
                instance = Repository(context)
            }
            return instance
        }
    }

    fun updateToDoList(toDoList: ToDoList) {
        toDoListDao.updateParticipantsList(toDoList.title, toDoList.participants)
    }

    fun addNote(toDoList: ToDoList, note: Note) {
        toDoList.notes.notesList.add(note)

        firebaseManager.updateToDoList(toDoList).addOnSuccessListener {
            thread(start = true) {
                toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
            }
        }
    }

    fun updateNoteImage(toDoList: ToDoList) {
        toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
    }

    private fun getPaginationLists(dbToDoLists: LiveData<List<ToDoList>>) {
        firebaseManager.getToDoListPagination().addOnSuccessListener {
            for (d in it.documents) {
                if (d.data != null) {
                    val toDoList = d.toObject(ToDoList::class.java)
                    if (!dbToDoLists.value!!.contains(toDoList)) {
                        thread(start = true) {
                            toDoListDao.insertToDoList(toDoList!!)
                        }
                    }
                }
            }
        }
    }

    private fun getAllLists(user: User, dbToDoLists: LiveData<List<ToDoList>>) {
        for (toDoList in user.toDoLists) {
            firebaseManager.getToDoList(toDoList).addOnSuccessListener {
                if (it.data != null) {
                    val toDoList = it.toObject(ToDoList::class.java)
                    if (!dbToDoLists.value!!.contains(toDoList)) {
                        thread(start = true) {
                            toDoListDao.insertToDoList(toDoList!!)
                        }
                    }
                }
            }
        }
    }

    //todo::::
    fun getAllToDoLists(): LiveData<List<ToDoList>> {
        val dbToDoLists = toDoListDao.getAllToDoLists()
        firebaseManager.getUser().addOnSuccessListener { userJson ->
            if (userJson.data != null) {
                val user = userJson.toObject(User::class.java)
                getAllLists(user!!, dbToDoLists)
                getPaginationLists( dbToDoLists)
            }
        }
        return dbToDoLists
    }

    fun addToDoList(toDoList: ToDoList) {
        firebaseManager.updateToDoList(toDoList).addOnSuccessListener {
            thread(start = true) {
                toDoListDao.insertToDoList(toDoList)
            }
        }
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

    fun addParticipant(coroutineScope: CoroutineScope, email: String, toDoList: ToDoList) {
        firebaseManager.getUser(email).addOnSuccessListener { document ->
            thread(start = true) {
                if (document.data != null) {
                    val user = document.toObject(User::class.java)
                    toDoList.participants.usersList.add(user!!)
                    firebaseManager.updateToDoList(toDoList).addOnSuccessListener {
                        thread(start = true) {
                            updateToDoList(toDoList)
                        }
                    }
                }
            }
        }
    }

//    fun getUsersByToDoList(toDoList: ToDoList): LiveData<List<User>> {
//        return toDoListDao.getToDoListUsers(toDoList.id)
//    }
}