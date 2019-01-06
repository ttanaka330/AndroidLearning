package com.github.ttanaka330.learning.todo.data

interface TaskRepository {

    fun load(id: Int): Task?

    fun loadAll(): List<Task>

    fun save(task: Task)

    fun delete(id: Int)
}