package com.ronyehezkel.helloworld.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val EMAIL_COL = "email"
const val FIRST_NAME_COL = "firstName"
const val LAST_NAME_COL = "lastName"

@Entity(tableName = "usersTable")
data class User(
    @PrimaryKey
    @ColumnInfo(name = EMAIL_COL) val email: String,
    @ColumnInfo(name = FIRST_NAME_COL) val firstName: String,
    @ColumnInfo(name = LAST_NAME_COL) val lastName: String,
){
    constructor() : this("", "","")
//    @PrimaryKey(autoGenerate = true)
//    var id = 0
}