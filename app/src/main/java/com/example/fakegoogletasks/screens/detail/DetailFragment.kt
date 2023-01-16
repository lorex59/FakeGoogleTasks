package com.example.fakegoogletasks.screens.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.databinding.FragmentDetailBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.screens.start.StartFragment.Companion.KEY_TO_TASK
import com.example.fakegoogletasks.utils.formatterCustom
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: TaskViewModel
    private lateinit var binding: FragmentDetailBinding
    lateinit var newTask: Task
    lateinit var task: Task
    lateinit var dateCalendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        dateCalendar = Calendar.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialListener()

    }

    // region InitialLister

    private fun initialListener() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        task = arguments?.getSerializable(KEY_TO_TASK) as Task

        binding.titleEditText.setText(task.title)
        binding.descriptionEditText.setText(task.description)

        if (task.date == Date(0)) {
            binding.timeEditText.setText("")
        } else {
            binding.timeEditText.setText(formatterCustom(task.date.time))
        }
        binding.favoriteCheckBox.isChecked = task.isFavorite

        binding.editButton.setOnClickListener {
            updateTask()
        }
        binding.timeEditText.setOnClickListener {
            showDatePicker()
        }
        binding.trashButton.setOnClickListener {
            viewModel.deleteTask(task)
            findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }
    }

    //endregion

    // region DatePicker

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

    //region UpdateTask

    private fun updateTask() {

        newTask = Task(task.id,
            binding.titleEditText.text.toString(),
            binding.descriptionEditText.text.toString(),
            dateCalendar.time,
            task.isFinish,
            binding.favoriteCheckBox.isChecked,
            null
        )
        viewModel.updateTask(newTask)
        findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
    }

    //endregion

}