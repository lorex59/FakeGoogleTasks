package com.example.fakegoogletasks.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fakegoogletasks.entity.Task
import kotlinx.coroutines.flow.Flow


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

    @Query("SELECT * FROM task_table WHERE parent_id IS NULL ORDER BY id ASC")
    fun readAllTask(): LiveData<List<Task>>

    @Query("SELECT id FROM task_table WHERE title = :title AND description = :description")
    suspend fun findOne(title: String, description: String): Int

    @Query("SELECT * FROM task_table WHERE parent_id = :id")
    suspend fun findChildrenById(id: Int): List<Task>

    @Query("SELECT MAX(id) FROM task_table")
    suspend fun findMaxId(): Int

}