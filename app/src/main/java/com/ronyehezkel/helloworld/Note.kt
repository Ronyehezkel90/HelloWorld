package com.ronyehezkel.helloworld

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
data class Note(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}