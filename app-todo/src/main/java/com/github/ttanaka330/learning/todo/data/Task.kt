package com.github.ttanaka330.learning.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)
