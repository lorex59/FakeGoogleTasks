package com.example.fakegoogletasks.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding
import com.example.fakegoogletasks.databinding.ItemLayoutBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.screens.start.StartFragment
import com.example.fakegoogletasks.utils.formatterCustom
import com.example.fakegoogletasks.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

interface TaskActionListener {

    fun onTaskDetail(task: Task)

    fun onDeleteButton(task: Task)

    fun onFavoriteButton(task: Task)

}


class TaskAdapter(private val taskActionListener: TaskActionListener, private val type: String) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()
    private lateinit var newTask: Task

    inner class TaskViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)


        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
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
        with(holder.binding) {

            titleTextView.text = currentItem.title
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
        when(type) {
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