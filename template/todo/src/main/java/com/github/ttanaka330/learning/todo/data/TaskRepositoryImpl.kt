package com.github.ttanaka330.learning.todo.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class TaskRepositoryImpl private constructor(context: Context) : TaskRepository {

    companion object {
        @Volatile
        private var INSTANCE: TaskRepositoryImpl? = null

        fun getInstance(context: Context): TaskRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepositoryImpl(context).also { INSTANCE = it }
            }
    }

    private val database: SQLiteDatabase = TodoDatabase(context).writableDatabase

    override fun load(id: Int): Task? {
        val query = "SELECT * FROM ${TodoDatabase.TABLE_NAME}" +
                " WHERE ${TodoDatabase.COLUMN_ID} = ?"
        val queryArgs = arrayOf(id.toString())
        var task: Task? = null
        database.rawQuery(query, queryArgs).use {
            if (it.moveToNext()) {
                task = createTask(it)
            }
        }
        return task
    }

    override fun loadAll(): List<Task> {
        val query = "SELECT * FROM ${TodoDatabase.TABLE_NAME}" +
                " ORDER BY ${TodoDatabase.COLUMN_ID} DESC"
        var tasks: MutableList<Task> = mutableListOf()
        database.rawQuery(query, null).use {
            while (it.moveToNext()) {
                tasks.add(createTask(it))
            }
        }
        return tasks
    }

    override fun save(task: Task) {
        val values = ContentValues().apply {
            put(TodoDatabase.COLUMN_TITLE, task.title)
            put(TodoDatabase.COLUMN_DESCRIPTION, task.description)
            put(TodoDatabase.COLUMN_COMPLETED, task.completed)
        }
        if (task.id == null) {
            database.insert(TodoDatabase.TABLE_NAME, null, values)
        } else {
            val where = "${TodoDatabase.COLUMN_ID} = ?"
            val whereArgs = arrayOf(task.id.toString())
            database.update(TodoDatabase.TABLE_NAME, values, where, whereArgs)
        }
    }

    override fun delete(id: Int) {
        val where = "${TodoDatabase.COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())
        database.delete(TodoDatabase.TABLE_NAME, where, whereArgs)
    }

    private fun createTask(cursor: Cursor): Task =
        Task(
            id = cursor.getInt(cursor.getColumnIndex(TodoDatabase.COLUMN_ID)),
            title = cursor.getString(cursor.getColumnIndex(TodoDatabase.COLUMN_TITLE)),
            description = cursor.getString(cursor.getColumnIndex(TodoDatabase.COLUMN_DESCRIPTION)),
            completed = cursor.getInt(cursor.getColumnIndex(TodoDatabase.COLUMN_COMPLETED)) > 0
        )
}