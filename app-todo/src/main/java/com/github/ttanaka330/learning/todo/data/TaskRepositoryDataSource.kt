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
            .allowMainThreadQueries() // メインスレッドで処理をする場合に使用する（基本使わない）
            .build()

    override fun load(id: Int): Task? {
        return database.taskDao().load(id)
    }

    override fun loadList(isCompleted: Boolean): List<Task> {
        return database.taskDao().loadList(isCompleted)
    }

    override fun save(task: Task) {
        database.taskDao().save(task)
    }

    override fun delete(id: Int) {
        database.taskDao().delete(id)
    }

    override fun deleteCompleted() {
        database.taskDao().deleteCompleted()
    }
}
