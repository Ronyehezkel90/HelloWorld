package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ronyehezkel.helloworld.model.*

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
}