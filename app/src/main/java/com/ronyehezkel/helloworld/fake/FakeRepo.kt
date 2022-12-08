package com.ronyehezkel.helloworld.fake

import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.IRepository
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.flow.Flow

class FakeRepo: IRepository {
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

    override fun getLocalToDoListsLiveData(): LiveData<List<ToDoList>> {
        TODO("Not yet implemented")
    }

    override fun getLocalToDoLists(): List<ToDoList> {
        TODO("Not yet implemented")
    }

    override fun addToDoListToLocalStorage(toDoList: ToDoList) {
        TODO("Not yet implemented")
    }

    override fun updateFcmToken() {
        TODO("Not yet implemented")
    }

    override fun loadRemoteToDoLists(): Flow<FlowEvent> {
        TODO("Not yet implemented")
    }
}