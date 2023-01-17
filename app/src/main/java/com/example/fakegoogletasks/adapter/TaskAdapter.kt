package com.example.fakegoogletasks.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakegoogletasks.databinding.ItemLayoutBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.screens.main.MainFragment
import com.example.fakegoogletasks.utils.formatterCustom
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

interface TaskActionListener {

    fun onTaskDetail(task: Task)

    fun onDeleteButton(task: Task)

    fun onFavoriteButton(task: Task)

    fun findById(id: Int): List<Task>
}


class TaskAdapter(private val taskActionListener: TaskActionListener, private val type: String) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()
    private lateinit var newTask: Task
    private lateinit var subTasks: List<Task>
    lateinit var context: Context
    private var adapter = SubTaskAdapter()


    inner class TaskViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        with(holder.binding) {
            holder.binding.dateTextView.isVisible = true
            val temp = formatterCustom(currentItem.date.time)
            holder.binding.root.setOnClickListener {
                taskActionListener.onTaskDetail(currentItem)
            }
            holder.binding.checkBoxDelete.setOnClickListener {
                newTask = currentItem.copy(isFinish = holder.binding.checkBoxDelete.isChecked)
                taskActionListener.onDeleteButton(newTask)
            }
            holder.binding.checkBoxFavorite.setOnClickListener {
                newTask = currentItem.copy(isFavorite = holder.binding.checkBoxFavorite.isChecked)
                taskActionListener.onFavoriteButton(newTask)
            }
            titleTextView.text = currentItem.title.toString()
            if (currentItem.description != "")
                descriptionTextView.text = currentItem.description
            else
                descriptionTextView.isVisible = !descriptionTextView.isVisible
            checkBoxDelete.isChecked = currentItem.isFinish
            checkBoxFavorite.isChecked = currentItem.isFavorite
            if (currentItem.date != Date(0))
                dateTextView.text = temp
            else
                dateTextView.isVisible = !dateTextView.isVisible
        }

    }


    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(tasks: List<Task>) {
        when (type) {
            TYPE_FAVORITE -> this.taskList = tasks.filter { it.isFavorite }
            TYPE_FINISHED -> this.taskList = tasks.filter { it.isFinish }
            TYPE_BASE -> this.taskList = tasks.filter { !it.isFinish }
        }

        notifyDataSetChanged()
    }

    companion object {

        const val TYPE_FAVORITE = "TYPE_FAVORITE"
        const val TYPE_FINISHED = "TYPE_FINISHED"
        const val TYPE_BASE = "TYPE_BASE"

    }

}