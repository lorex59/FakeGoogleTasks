package com.example.fakegoogletasks.screens.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.entity.Task
import com.example.fakegoogletasks.viewmodels.TaskViewModel


class SplashFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readAllData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        })
    }
}