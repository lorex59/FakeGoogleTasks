package com.example.fakegoogletasks.screens.start

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.adapter.TaskActionListener
import com.example.fakegoogletasks.adapter.TaskAdapter
import com.example.fakegoogletasks.adapter.TaskAdapter.Companion.TYPE_BASE
import com.example.fakegoogletasks.databinding.FragmentStartBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.utils.showToast
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import kotlinx.android.synthetic.main.fragment_start.view.*


class StartFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater, container, false)

        val adapter = TaskAdapter(object : TaskActionListener {
            override fun onTaskDetail(task: Task) {
                val bundle = Bundle()
                bundle.putSerializable(KEY_TO_TASK, task) //todo
                findNavController().navigate(R.id.action_startFragment_to_detailFragment, bundle)
            }

            override fun onDeleteButton(task: Task) {
                viewModel.updateTask(task)
            }

            override fun onFavoriteButton(task: Task) {
                viewModel.updateTask(task)
            }

        }, TYPE_BASE)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_addTaskFragment)
        }
        binding.settingButton.setOnClickListener {
            showToast("setting")
        }
        binding.menuButton.setOnClickListener {
            showToast("menu")
        }
        binding.trashAllButton.setOnClickListener {
            deleteAllUsers()
        }

    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    companion object {

        const val KEY_TO_TASK = "KEY_TO_TASK"

    }

}