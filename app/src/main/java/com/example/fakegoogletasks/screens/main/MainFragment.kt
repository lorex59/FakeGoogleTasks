package com.example.fakegoogletasks.screens.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.adapter.ViewPagerAdapter
import com.example.fakegoogletasks.databinding.FragmentMainBinding
import com.example.fakegoogletasks.utils.showToast
import com.example.fakegoogletasks.viewmodels.TaskViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    lateinit var viewModel: TaskViewModel
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialListener()
        initialViewPager()
    }

    //region Initial

    private fun initialViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Текущие задачи"
                1 -> tab.text = "Избранное"
                2 -> tab.text = "Выполненные"
            }
        }.attach()
    }

    private fun initialListener() {
        binding.settingButton.setOnClickListener {
            showToast("setting")
        }
        binding.menuButton.setOnClickListener {
            showToast("menu")
        }
        binding.trashAllButton.setOnClickListener {
            deleteAllTasks()
        }

        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addTaskFragment)

        }
    }

    //endregion

    // region AlertDialog

    private fun deleteAllTasks() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да") { _, _ ->
            viewModel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Все задачи удалены",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Нет") { _, _ -> }
        builder.setTitle("Удалить все?")
        builder.setMessage("Вы уверены, что хотите все удалить?")
        builder.create().show()
    }

    //endregion

}