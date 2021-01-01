package com.github.ttanaka330.learning.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id = :id")
    fun load(id: Int): Task?

    @Query("SELECT * FROM task WHERE completed = :isCompleted ORDER BY id DESC")
    fun loadList(isCompleted: Boolean): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(task: Task)

    // @Delete
    @Query("DELETE FROM task WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM task WHERE completed = 1")
    fun deleteCompleted()
}
