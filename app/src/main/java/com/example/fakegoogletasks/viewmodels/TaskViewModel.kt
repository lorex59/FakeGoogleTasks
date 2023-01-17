package com.example.fakegoogletasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fakegoogletasks.database.TaskDatabase
import com.example.fakegoogletasks.database.repository.TaskRepository
import com.example.fakegoogletasks.entity.Task
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Task>>
    private val repository: TaskRepository

    //lateinit var maxId: LiveData<Int?>
    lateinit var currentTask: LiveData<Int>

    init {
        val taskDao = TaskDatabase.getDatabase(
            application
        ).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.readAllData
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
            //repository.findOne(task.title, task.description)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)

        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    suspend fun findOne(title: String, description: String): Int {
        val temp: Deferred<Int> = viewModelScope.async {
            repository.findOne(title, description)
        }
        return temp.await()
    }

    suspend fun findChildrenById(id: Int): List<Task> {
        val temp: Deferred<List<Task>> = viewModelScope.async {
            repository.findChildrenById(id)
        }
        return temp.await()
    }

}