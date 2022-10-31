package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ronyehezkel.helloworld.FirebaseManager
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app.applicationContext)
    val toDoListLiveData: MutableLiveData<ToDoList> = MutableLiveData()
    val firebaseManager = FirebaseManager.getInstance(app.applicationContext)

    fun getNotesLiveData(toDoList: ToDoList): LiveData<NotesList> {
        return repository.getNotesByToDoList(toDoList)
    }

    fun getUserLiveData(toDoList: ToDoList): LiveData<Participants> {
        return repository.getUsersByToDoList(toDoList)
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(toDoListLiveData.value!!, note)
        }
    }

    fun addUser(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseManager.getUser(email).addOnSuccessListener { document ->
                if (document.data != null) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val user = document.toObject(User::class.java)
//                        Firestore.getInstance(app).addParticipant(user!!).addOnSuccessListener {
                            repository.addUserToToDoList(toDoListLiveData.value!!, user!!)
//                        }
                    }
                } else {
                    Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun createToDoList(toDoList: ToDoList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToDoList(toDoList)
        }
    }

    fun setCurrentToDoList(toDoList: ToDoList) {
        toDoListLiveData.value = toDoList
    }

    suspend fun getToDoListByTitle(toDoListTitle: String): ToDoList {
        var toDoList: ToDoList? = null
        val work = viewModelScope.launch(Dispatchers.IO) {
            toDoList = repository.getToDoListByTitle(toDoListTitle)
        }
        work.join()
        return toDoList!!
    }
}