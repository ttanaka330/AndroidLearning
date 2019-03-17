package com.github.ttanaka330.learning.todo.data

import android.content.Context
import androidx.room.Room

class TaskRepositoryDataSource private constructor(context: Context) : TaskRepository {

    companion object {
        @Volatile
        private var INSTANCE: TaskRepositoryDataSource? = null

        fun getInstance(context: Context): TaskRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepositoryDataSource(context).also { INSTANCE = it }
            }
    }

    private val database =
        Room.databaseBuilder(context, TodoDatabase::class.java, TodoDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()

    override fun load(id: Int): Task? {
        return database.taskDao().load(id)?.toModel()
    }

    override fun loadList(isCompleted: Boolean): List<Task> {
        return database.taskDao().loadList(isCompleted).map { it.toModel() }
    }

    override fun save(task: Task) {
        database.taskDao().save(task.toEntity())
    }

    override fun delete(id: Int) {
        database.taskDao().load(id)?.let {
            database.taskDao().delete(it)
        }
    }

    override fun deleteCompleted() {
        database.taskDao().deleteCompleted()
    }

    private fun Task.toEntity(): TaskEntity =
        TaskEntity(
            id = if (this.id == Task.NEW_TASK_ID) null else this.id,
            title = this.title,
            description = this.description,
            completed = this.completed
        )

    private fun TaskEntity.toModel(): Task =
        Task(
            id = this.id ?: Task.NEW_TASK_ID,
            title = this.title,
            description = this.description,
            completed = this.completed
        )
}