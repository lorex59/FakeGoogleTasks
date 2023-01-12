package com.example.fakegoogletasks.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import java.util.*


class AddTaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addTaskFragment_to_startFragment)
        }
        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val date = Calendar.getInstance().time
        val newTask = Task(
            0,
            title,
            description,
            date,
            false,
            false,
            null
        )
        taskViewModel.addTask(newTask)
    }

}