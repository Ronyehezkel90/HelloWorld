package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ronyehezkel.helloworld.IRepository
import com.ronyehezkel.helloworld.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(val repository: IRepository) : ViewModel() {
    private var countLetters = 0

    fun getAllToDoListsAsLiveData(): LiveData<List<ToDoList>> {
        return repository.getLocalToDoListsLiveData()
    }

    fun updateLocalToDoLists(remoteToDoListList: List<ToDoList>) {
        if (!remoteToDoListList.isNullOrEmpty()) {
            val localToDoList = repository.getLocalToDoLists()
            remoteToDoListList.forEach { remoteToDoList ->
                var isExist = false
                localToDoList.forEach { localToDoList ->
                    if (remoteToDoList.title == localToDoList.title) {
                        isExist = true
                    }
                }
                if (!isExist) {
                    repository.addToDoListToLocalStorage(remoteToDoList)
                }
            }
        }
    }

    suspend fun loadToDoLists(): Flow<FlowEvent> {
        val toDoListFlow = repository.loadRemoteToDoLists()

        toDoListFlow
            .filter { it.message == Message.SUCCESS }
            .map { it.toDoList}
            .collect {
                updateLocalToDoLists(it!!)
//                Log.d("Hi im consumer 1 ", it)
            }

        return toDoListFlow
    }
}