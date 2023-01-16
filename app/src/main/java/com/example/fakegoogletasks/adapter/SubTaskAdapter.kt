package com.example.fakegoogletasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fakegoogletasks.databinding.ItemLayoutSubtaskBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.utils.formatterCustom
import java.util.*

class SubTaskAdapter: RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder>() {

    private var subTasks: MutableList<Task> = mutableListOf()

    inner class SubTaskViewHolder(val binding: ItemLayoutSubtaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutSubtaskBinding.inflate(inflater, parent, false)

        return SubTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        val currentItem = subTasks[position]
        holder.binding.dateTextView.isVisible = true
        val temp = formatterCustom(currentItem.date.time)

//        holder.binding.checkBoxDelete.setOnClickListener {
//            newTask = currentItem.copy(isFinish = holder.binding.checkBoxDelete.isChecked)
//            taskActionListener.onDeleteButton(newTask)
//        }
//        holder.binding.checkBoxFavorite.setOnClickListener {
//            newTask = currentItem.copy(isFavorite = holder.binding.checkBoxFavorite.isChecked)
//            taskActionListener.onFavoriteButton(newTask)
//        }
        with(holder.binding) {
            //if (currentItem.title.isNotEmpty())
                titleTextView.setText(currentItem.title)
            //if (currentItem.description.isNotEmpty())
                descriptionTextView.setText(currentItem.description)

            //descriptionTextView.isVisible = !descriptionTextView.isVisible
            checkBoxFavorite.isChecked = currentItem.isFavorite
            if (currentItem.date == Date(0))
                dateTextView.text = ""
            else
                dateTextView.text = temp
            //dateTextView.isVisible = !dateTextView.isVisible
        }
    }

    override fun getItemCount(): Int {
        return subTasks.size
    }

    fun setData(_subTasks: List<Task>) {
        subTasks = _subTasks as MutableList<Task>

        notifyDataSetChanged()
    }

    fun addSubTask(subTask: Task) {
        subTasks.add(subTask)
        notifyDataSetChanged()
        //notifyItemChanged()
    }

}