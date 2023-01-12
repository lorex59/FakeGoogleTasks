package com.example.fakegoogletasks.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fakegoogletasks.entity.Task


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllTask(): LiveData<List<Task>>

}