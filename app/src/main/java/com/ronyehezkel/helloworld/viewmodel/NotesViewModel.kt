package com.ronyehezkel.helloworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ronyehezkel.helloworld.model.Note
import com.ronyehezkel.helloworld.model.Repository

class NotesViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app.applicationContext)

    fun addNote(note: Note) {
        repository.addNote(note)
    }

    val notesListLiveData: LiveData<List<Note>> = repository.getAllNotesAsLiveData()

    val currentTitleLiveData: MutableLiveData<String> = MutableLiveData()
}