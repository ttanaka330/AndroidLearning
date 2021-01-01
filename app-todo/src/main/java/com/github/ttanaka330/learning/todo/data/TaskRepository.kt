package com.github.ttanaka330.learning.todo.data

import androidx.lifecycle.LiveData

interface TaskRepository {

    fun load(id: Int): LiveData<Task?>

    fun loadList(isCompleted: Boolean): LiveData<List<Task>>

    suspend fun save(task: Task)

    suspend fun delete(id: Int)

    suspend fun deleteCompleted()
}
