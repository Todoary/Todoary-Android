package com.uni.todoary.feature.main.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardVPAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 4 //4개의 fragment필요

    override fun createFragment(position: Int): Fragment {
        return OnBoardFragment(position)
    }
}