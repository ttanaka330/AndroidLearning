package com.github.ttanaka330.learning.todo.data

data class Task(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)