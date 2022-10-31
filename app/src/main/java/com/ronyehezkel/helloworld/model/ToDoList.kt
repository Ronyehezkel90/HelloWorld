package com.ronyehezkel.helloworld.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "toDoListTable")
data class ToDoList(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "participants")
    var participants: Participants = Participants(arrayListOf()),
    @ColumnInfo(name = "notes")
    var notes: NotesList = NotesList(arrayListOf()),
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    ){
    constructor() : this("")
}
