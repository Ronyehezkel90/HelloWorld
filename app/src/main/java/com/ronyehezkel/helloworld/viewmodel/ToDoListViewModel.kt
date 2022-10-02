package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app.applicationContext)

    fun getAllToDoListsAsLiveData(): LiveData<List<ToDoList>> {
        return repository.getAllToDoLists()
    }
}