package com.ronyehezkel.helloworld.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun userObjectToJson(notesList: NotesList): String{
        val gson = Gson()
        val type = object : TypeToken<NotesList>() {}.type
        return gson.toJson(notesList, type)
    }

    @TypeConverter
    fun jsonToUserObject(notesListAsJson: String): NotesList{
        val gson = Gson()
        val type = object : TypeToken<NotesList>() {}.type
        return gson.fromJson(notesListAsJson, type)
    }
}