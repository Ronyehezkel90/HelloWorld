package com.ronyehezkel.helloworld

import android.util.Log
import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getNotesByToDoList(toDoList: ToDoList): LiveData<NotesList>

    fun getUsersByToDoList(toDoList: ToDoList): LiveData<Participants>

    fun addNote(value: ToDoList, note: Note)

    fun addUserToToDoList(value: ToDoList, user: User)

    fun addToDoList(toDoList: ToDoList)

    fun getToDoListByTitle(toDoListTitle: String): ToDoList?

    fun getLocalToDoListsLiveData(): LiveData<List<ToDoList>>

    fun getLocalToDoLists(): List<ToDoList>

    fun addToDoListToLocalStorage(toDoList: ToDoList)

    fun updateFcmToken()

    fun loadRemoteToDoLists(): Flow<FlowEvent>
}