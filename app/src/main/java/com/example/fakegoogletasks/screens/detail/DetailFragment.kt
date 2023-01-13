package com.example.fakegoogletasks.screens.detail

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class DetailFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var binding: FragmentDetailBinding
    lateinit var newTask: Task
    lateinit var task: Task
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_startFragment)
        }

        task = arguments?.getSerializable(KEY_TO_TASK) as Task
        binding.titleEditText.setText(task.title)
        binding.descriptionEditText.setText(task.description)
        binding.timeEditText.setText(formatterCustom(task.date.time))
        binding.favoriteCheckBox.isChecked = task.isFavorite

        binding.editButton.setOnClickListener {
            updateTask()
        }
    }

    private fun updateTask() {

        newTask = Task(task.id,
            binding.titleEditText.text.toString(),
            binding.descriptionEditText.text.toString(),
            task.date,
            task.isFinish,
            binding.favoriteCheckBox.isChecked,
            null
        )
        viewModel.updateTask(newTask)
        findNavController().navigate(R.id.action_detailFragment_to_startFragment)
    }

}