package com.github.ttanaka330.learning.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id IN (:id)")
    fun load(id: Int): TaskEntity?

    @Query("SELECT * FROM task WHERE completed = :isCompleted ORDER BY id DESC")
    fun loadList(isCompleted: Boolean): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

    @Query("DELETE FROM task WHERE completed = 1")
    fun deleteCompleted()
}