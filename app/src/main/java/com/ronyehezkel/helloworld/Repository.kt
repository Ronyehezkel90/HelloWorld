package com.ronyehezkel.helloworld

import android.app.Application
import androidx.lifecycle.LiveData

class Repository(application: Application) {
    private val dao = NotesDatabase.getDatabase(application).getNotesDao()

    fun getAllNotesAsLiveData(): LiveData<List<Note>> {
//        if(yeshKlita){
//            return server.getAllNotes()
//        }
//        else{
            return dao.getAllNotes()
//        }
    }

    fun addNote(note: Note) {
        dao.insertNote(note)
    }
}