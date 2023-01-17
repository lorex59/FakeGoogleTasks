package com.example.fakegoogletasks.adapter

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fakegoogletasks.databinding.ItemLayoutSubtaskBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.utils.CustomTextWatcher
import com.example.fakegoogletasks.utils.formatterCustom
import java.util.*

class SubTaskAdapter : RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder>() {

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

        with(holder.binding) {
            titleTextView.setText(currentItem.title)
            descriptionTextView.setText(currentItem.description)
            checkBoxFavorite.isChecked = currentItem.isFavorite

            checkBoxFavorite.setOnClickListener {
                subTasks[position].isFavorite = !subTasks[position].isFavorite
            }

            //region TextWatcher

            titleTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    subTasks[position].title = holder.binding.titleTextView.text.toString()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            descriptionTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    subTasks[position].description =
                        holder.binding.descriptionTextView.text.toString()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            //endregion

        }
    }

    override fun getItemCount(): Int {
        return subTasks.size
    }


    //region Function

    @SuppressLint("NotifyDataSetChanged")
    fun setData(_subTasks: List<Task>) {
        if (_subTasks.isNotEmpty())
            subTasks = _subTasks as MutableList<Task>
        Log.d("Tag", "I am here1 + ${_subTasks.toString()}")
        Log.d("Tag", "I am here2 + ${subTasks.toString()}")
        notifyDataSetChanged()
    }

    fun getAllSubTasks(): MutableList<Task> {
        return subTasks
    }

    fun addSubTask(subTask: Task) {
        subTasks.add(subTask)
        //notifyDataSetChanged()
        notifyItemChanged(subTasks.size)
    }

    //endregion

}