package com.ronyehezkel.helloworld.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toDoListTable")
data class ToDoList(
    @PrimaryKey
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "participants")
    var participants: Participants = Participants(arrayListOf()),
    @ColumnInfo(name = "notes")
    var notes: NotesList = NotesList(arrayListOf()),
    var timestamp: Long = System.currentTimeMillis()
)
{
    constructor() : this("")
}
