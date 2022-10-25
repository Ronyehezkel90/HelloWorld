package com.ronyehezkel.helloworld.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val EMAIL_COL = "email"
const val FIRST_NAME_COL = "firstName"
const val LAST_NAME_COL = "lastName"
const val TO_DO_LISTS_COL = "toDoLists"
const val IMAGE_PATH_COL = "imagePath"
const val FCM_TOKEN_COL = "fcmToken"

@Entity(tableName = "usersTable")
data class User(
    @PrimaryKey
    @ColumnInfo(name = EMAIL_COL) val email: String,
    @ColumnInfo(name = FIRST_NAME_COL) val firstName: String,
    @ColumnInfo(name = LAST_NAME_COL) val lastName: String,
    @ColumnInfo(name = TO_DO_LISTS_COL) val toDoLists: MutableList<String> = arrayListOf(),
    @ColumnInfo(name = IMAGE_PATH_COL) var imagePath: String? = null,
    @ColumnInfo(name = FCM_TOKEN_COL) var fcmToken: String? = null,

){
    constructor() : this("", "","", arrayListOf(), "")
//    @PrimaryKey(autoGenerate = true)
//    var id = 0
}