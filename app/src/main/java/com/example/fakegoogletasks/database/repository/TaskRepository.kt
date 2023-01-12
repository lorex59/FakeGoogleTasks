package com.example.fakegoogletasks.database.repository

import androidx.lifecycle.LiveData
import com.example.fakegoogletasks.database.dao.TaskDao
import com.example.fakegoogletasks.entity.Task

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllTask()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteUser(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllUsers() {
        taskDao.deleteAllTask()
    }

}