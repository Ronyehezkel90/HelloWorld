package com.ronyehezkel.helloworld.model

data class Participants(
    val usersList: ArrayList<User>
){
    constructor():this(arrayListOf())
}
