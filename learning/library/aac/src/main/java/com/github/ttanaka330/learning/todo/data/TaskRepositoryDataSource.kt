package com.github.ttanaka330.learning.todo.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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
            // .allowMainThreadQueries() // メインスレッドで処理をする場合に使用する（基本使わない）
            .build()

    override fun load(id: Int): LiveData<Task> {
        return Transformations.map(database.taskDao().load(id)) { it?.toModel() }
    }

    override fun loadList(isCompleted: Boolean): LiveData<List<Task>> {
        return Transformations.map(database.taskDao().loadList(isCompleted)) { list ->
            list.map { it.toModel() }
        }
    }

    override fun save(task: Task) {
        database.taskDao().save(task.toEntity())
    }

    override fun delete(id: Int) {
        database.taskDao().delete(id)
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