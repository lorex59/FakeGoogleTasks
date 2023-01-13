package com.example.fakegoogletasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding
import com.example.fakegoogletasks.databinding.ItemLayoutBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.screens.start.StartFragment
import com.example.fakegoogletasks.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

interface TaskActionListener {

    fun onTaskDetail(task: Task)

}

class TaskAdapter(private val taskActionListener: TaskActionListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    inner class TaskViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        //binding.root.setOnClickListener(this)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        val temp = sdf.format(currentItem.date.time)

        holder.binding.root.setOnClickListener {
            taskActionListener.onTaskDetail(currentItem)
        }

        with(holder.binding) {

            titleTextView.text = currentItem.title
            if (currentItem.description != "" )
                descriptionTextView.text = currentItem.description
            else
                descriptionTextView.isVisible = !descriptionTextView.isVisible
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
        this.taskList = tasks
        notifyDataSetChanged()
    }
}