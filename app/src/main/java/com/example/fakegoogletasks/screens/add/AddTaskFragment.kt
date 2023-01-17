package com.example.fakegoogletasks.screens.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.adapter.SubTaskAdapter
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.utils.showToast
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.properties.Delegates


class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var adapter: SubTaskAdapter
    var maxId by Delegates.notNull<Int>()
    lateinit var dateCalendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        dateCalendar = Calendar.getInstance()
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val recycler = binding.recyclerView
        adapter = SubTaskAdapter()
        recycler.adapter = adapter
        //Executors.newSingleThreadExecutor()
        recycler.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialListener()
    }

    //region InitialListener

    private fun initialListener() {

        binding.addButtonSubTask.setOnClickListener {
            val newSubTask = Task(0, "", "", Date(0), false, false, 0)
            adapter.addSubTask(newSubTask)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addTaskFragment_to_mainFragment)
        }

        binding.addButton.setOnClickListener {
            if(binding.titleEditText.text.isEmpty()) {
                showToast("Название задачи должно быть заполнено обязательно.")
            } else {
                insertDataToDatabase()
            }
        }
        binding.timeEditText.setOnClickListener {
            showDatePicker()
        }

    }

    //endregion

    //region DataBase

    private fun insertDataToDatabase() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val date = if (binding.timeEditText.text.toString() != "") {
            dateCalendar.time
        } else {
            Date(0)
        }
        val newTask = Task(
            0,
            title,
            description,
            date,
            false,
            binding.favoriteCheckBox.isChecked,
            null
        )
        taskViewModel.addTask(newTask)
        CoroutineScope(Main).launch {
            maxId = taskViewModel.findOne(newTask.title, newTask.description)
            for (subTask in adapter.getAllSubTasks()) {
                val newSubTask = subTask.copy(parent_id = maxId)
                taskViewModel.addTask(newSubTask)
            }
        }


        findNavController().navigate(R.id.action_addTaskFragment_to_mainFragment)
    }

    //endregion

    //region DatePicker

    private fun showDatePicker() {
        DatePickerDialog(
            requireContext(),
            R.style.datePicker,
            this,
            dateCalendar.get(Calendar.YEAR),
            dateCalendar.get(Calendar.MONTH),
            dateCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateCalendar.set(year, month, dayOfMonth)
        updateLable(dateCalendar)
    }

    private fun updateLable(myCalendarView: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        val temp = sdf.format(myCalendarView.time)
        binding.timeEditText.setText(temp)

    }

    //endregion

}