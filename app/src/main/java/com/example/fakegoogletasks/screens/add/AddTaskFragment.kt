package com.example.fakegoogletasks.screens.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var taskViewModel: TaskViewModel
    lateinit var binding: FragmentAddTaskBinding
    lateinit var dateCalendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        dateCalendar = Calendar.getInstance()
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
        binding.timeEditText.setOnClickListener {
            showDatePicker()
        }
    }

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
    }

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

}