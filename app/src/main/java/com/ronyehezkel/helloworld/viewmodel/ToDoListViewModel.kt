package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.flow.Flow

class ToDoListViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app.applicationContext)
    private var countLetters = 0

    fun getAllToDoListsAsLiveData(): LiveData<List<ToDoList>> {
        return repository.getLocalToDoLists()
    }

    fun updateLocalToDoLists() {
        repository.getPartOfToDoLists(countLetters)
        countLetters += 3
    }

    fun getRemoteToDoLists():List<ToDoList> {
        TODO("Not yet implemented")
    }

    fun loadToDoLists(): Flow<FlowEvent> {
        return repository.loadToDoLists()
    }
}