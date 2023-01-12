package com.example.fakegoogletasks.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fakegoogletasks.R
import com.example.fakegoogletasks.databinding.FragmentStartBinding
import com.example.fakegoogletasks.utils.showToast
import kotlinx.android.synthetic.main.fragment_start.view.*


class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater, container, false)

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
    }


}