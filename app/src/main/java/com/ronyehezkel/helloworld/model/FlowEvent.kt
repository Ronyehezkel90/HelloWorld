package com.ronyehezkel.helloworld.model

enum class Message {
    DONT_WORRY,
    SUCCESS,
    FAIL
}

class FlowEvent(
    var message: Message,
    val error: String? = null,
    val toDoList: List<ToDoList>? = null
)