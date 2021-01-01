package com.github.ttanaka330.learning.todo.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TaskRepositoryDataSource(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {

    override fun load(id: Int): LiveData<Task?> {
        return taskDao.load(id)
    }

    override fun loadList(isCompleted: Boolean): LiveData<List<Task>> {
        return taskDao.loadList(isCompleted)
    }

    override suspend fun save(task: Task) {
        withContext(ioDispatcher) {
            taskDao.save(task)
        }
    }

    override suspend fun delete(id: Int) {
        withContext(ioDispatcher) {
            taskDao.delete(id)
        }
    }

    override suspend fun deleteCompleted() {
        withContext(ioDispatcher) {
            taskDao.deleteCompleted()
        }
    }
}
