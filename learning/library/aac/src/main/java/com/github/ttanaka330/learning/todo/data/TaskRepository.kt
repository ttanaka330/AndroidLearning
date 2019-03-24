package com.github.ttanaka330.learning.todo.data

import androidx.lifecycle.LiveData

interface TaskRepository {

    fun load(id: Int): LiveData<Task>

    fun loadList(isCompleted: Boolean): LiveData<List<Task>>

    fun save(task: Task)

    fun delete(id: Int)

    fun deleteCompleted()
}