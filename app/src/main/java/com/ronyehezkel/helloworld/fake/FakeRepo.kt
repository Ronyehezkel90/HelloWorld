package com.ronyehezkel.helloworld.fake

import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.RepositoryI
import com.ronyehezkel.helloworld.model.*

class FakeRepo: RepositoryI {
    override fun getNotesByToDoList(toDoList: ToDoList): LiveData<NotesList> {
        TODO("Not yet implemented")
    }

    override fun getUsersByToDoList(toDoList: ToDoList): LiveData<Participants> {
        TODO("Not yet implemented")
    }

    override fun addNote(value: ToDoList, note: Note) {
        TODO("Not yet implemented")
    }

    override fun addUserToToDoList(value: ToDoList, user: User) {
        TODO("Not yet implemented")
    }

    override fun addToDoList(toDoList: ToDoList) {
        TODO("Not yet implemented")
    }

    override fun getToDoListByTitle(toDoListTitle: String): ToDoList? {
        TODO("Not yet implemented")
    }
}