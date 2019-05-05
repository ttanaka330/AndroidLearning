package com.github.ttanaka330.learning.todo.data

interface TaskRepository {

    suspend fun load(id: Int): Task?

    suspend fun loadList(isCompleted: Boolean): List<Task>

    suspend fun save(task: Task)

    suspend fun delete(id: Int)

    suspend fun deleteCompleted()
}