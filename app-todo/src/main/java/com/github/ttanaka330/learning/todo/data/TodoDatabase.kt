package com.github.ttanaka330.learning.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "todo.db"

        fun createDatabase(context: Context): TodoDatabase {
            return Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME)
                //.allowMainThreadQueries() // メインスレッドで処理をする場合に使用する（基本使わない）
                .build()
        }
    }
}
