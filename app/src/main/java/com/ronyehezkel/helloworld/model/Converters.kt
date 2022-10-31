package com.ronyehezkel.helloworld.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toUsersList(participants: String): Participants {
        val gson = Gson()
        val type = object : TypeToken<Participants>() {}.type
        return gson.fromJson(participants, type)
    }

    @TypeConverter
    fun fromUsersList(participants: Participants): String {
        val gson = Gson()
        val type = object : TypeToken<Participants>() {}.type
        return gson.toJson(participants, type)
    }

    @TypeConverter
    fun toNotesList(notes: String): NotesList {
        val gson = Gson()
        val type = object : TypeToken<NotesList>() {}.type
        return gson.fromJson(notes, type)
    }

    @TypeConverter
    fun fromNotesList(notes: NotesList): String {
        val gson = Gson()
        val type = object : TypeToken<NotesList>() {}.type
        return gson.toJson(notes, type)
    }

//    @TypeConverter
//    fun fromNote(note: Note): String {
//        val gson = Gson()
//        val type = object : TypeToken<Note>() {}.type
//        return gson.toJson(note, type)
//    }

//    @TypeConverter
//    fun toNote(note: String): Note {
//        val gson = Gson()
//        val type = object : TypeToken<Note>() {}.type
//        return gson.fromJson(note, type)
//    }
}
