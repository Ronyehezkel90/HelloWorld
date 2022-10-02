package com.ronyehezkel.helloworld.model

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoListDao {

    @Insert
    fun insertToDoList(toDoList: ToDoList)

    @Delete
    fun delete(toDoList: ToDoList)

    @Query("Select * from toDoListTable")
    fun getAllToDoLists(): LiveData<List<ToDoList>>

    @Query("UPDATE toDoListTable SET notes=:notes WHERE title = :title")
    fun updateNotesList(title: String, notes: NotesList)

    @Query("UPDATE toDoListTable SET participants=:participants WHERE title = :title")
    fun updateParticipantsList(title: String, participants: Participants)

    @Query("Select notes from toDoListTable where title = :title")
    fun getAllNotes(title: String): LiveData<NotesList>

    @Query("Select * from toDoListTable where title = :title")
    fun getToDoListByTitle(title: String): ToDoList

    @Query("Select participants from toDoListTable where title = :title")
    fun getAllUsers(title: String): LiveData<Participants>
}