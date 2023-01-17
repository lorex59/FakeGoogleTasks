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

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTask()
    }

    suspend fun findOne(title: String, description: String): Int {
        return taskDao.findOne(title, description)
    }

    suspend fun findChildrenById(id: Int): List<Task> {
        return taskDao.findChildrenById(id)
    }

}