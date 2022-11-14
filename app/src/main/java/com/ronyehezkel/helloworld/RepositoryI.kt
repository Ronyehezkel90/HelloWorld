package com.ronyehezkel.helloworld

import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.model.*

interface RepositoryI {
    fun getNotesByToDoList(toDoList: ToDoList): LiveData<NotesList>

    fun getUsersByToDoList(toDoList: ToDoList): LiveData<Participants>

    fun addNote(value: ToDoList, note: Note)

    fun addUserToToDoList(value: ToDoList, user: User)

    fun addToDoList(toDoList: ToDoList)

    fun getToDoListByTitle(toDoListTitle: String): ToDoList?
}