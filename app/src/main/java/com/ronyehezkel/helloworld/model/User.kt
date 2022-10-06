package com.ronyehezkel.helloworld.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usersTable")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "notes")
    val notes: NotesList,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null
)
{
    constructor(): this("", "", "", NotesList())
}