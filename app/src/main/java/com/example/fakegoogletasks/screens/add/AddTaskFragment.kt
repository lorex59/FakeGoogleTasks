package com.example.fakegoogletasks.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.databinding.FragmentAddTaskBinding


class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


}