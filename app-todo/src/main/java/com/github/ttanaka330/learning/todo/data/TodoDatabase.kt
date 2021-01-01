package com.github.ttanaka330.learning.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "todo.db"
    }

    abstract fun taskDao(): TaskDao
}
