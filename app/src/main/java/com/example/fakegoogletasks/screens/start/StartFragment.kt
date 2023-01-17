package com.example.fakegoogletasks.screens.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.adapter.TaskActionListener
import com.example.fakegoogletasks.adapter.TaskAdapter
import com.example.fakegoogletasks.databinding.FragmentStartBinding
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class StartFragment : Fragment() {

    lateinit var viewModel: TaskViewModel
    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater, container, false)

        val adapter = arguments?.getString(MY_TYPE)?.let {
            TaskAdapter(object : TaskActionListener {
                override fun onTaskDetail(task: Task) {
                    val bundle = Bundle()
                    bundle.putSerializable(KEY_TO_TASK, task) //todo
                    findNavController().navigate(
                        R.id.action_mainFragment_to_detailFragment,
                        bundle
                    )
                }

                override fun onDeleteButton(task: Task) {
                    viewModel.updateTask(task)
                }

                override fun onFavoriteButton(task: Task) {
                    viewModel.updateTask(task)
                }

                override fun findById(id: Int): List<Task> {return emptyList()}


            }, it)
        }
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        CoroutineScope(Main).launch {
            viewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
                adapter?.setData(task)
            })

        }
        return binding.root
    }



    companion object {

        const val KEY_TO_TASK = "KEY_TO_TASK"
        private const val MY_TYPE = "MY_TYPE"

        fun newInstance(type: String) = StartFragment().apply {
            arguments = bundleOf(
                MY_TYPE to type
            )
        }
    }


}