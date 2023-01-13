package com.example.fakegoogletasks.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fakegoogletasks.adapter.TaskAdapter.Companion.TYPE_BASE
import com.example.fakegoogletasks.adapter.TaskAdapter.Companion.TYPE_FAVORITE
import com.example.fakegoogletasks.adapter.TaskAdapter.Companion.TYPE_FINISHED
import com.example.fakegoogletasks.screens.start.StartFragment

open class ViewPagerAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> StartFragment.newInstance(TYPE_FAVORITE)
            1 -> StartFragment.newInstance(TYPE_BASE)
            2 -> StartFragment.newInstance(TYPE_FINISHED)
            else -> StartFragment.newInstance(TYPE_FAVORITE)
        }
    }
}