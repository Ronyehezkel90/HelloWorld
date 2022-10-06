package com.ronyehezkel.helloworld.model

import androidx.room.*


@Dao
interface UsersDao {

    @Insert
    fun insertUser(user: User)
//    @Update
//    fun updateNote(note: Note)
}