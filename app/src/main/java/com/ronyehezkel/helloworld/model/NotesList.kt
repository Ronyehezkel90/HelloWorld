package com.ronyehezkel.helloworld.model

data class NotesList(
    val notesList: ArrayList<Note>
){
    constructor():this(arrayListOf())
}
