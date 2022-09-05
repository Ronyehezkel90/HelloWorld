package com.ronyehezkel.helloworld

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Select * from notesTable")
    fun getAllNotes(): LiveData<List<Note>>

}