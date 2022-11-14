package com.ronyehezkel.helloworld

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ronyehezkel.helloworld.viewmodel.NotesViewModel

class ViewModelFactory(val serverManager: ServerManager, val repository: RepositoryI): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(serverManager, repository) as T
    }
}