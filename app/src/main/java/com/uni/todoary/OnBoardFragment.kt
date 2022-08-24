package com.uni.todoary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uni.todoary.databinding.FragmentOnBoardBinding

class OnBoardFragment(position: Int) : Fragment() {
    lateinit var binding: FragmentOnBoardBinding
    private var onBoardImg = arrayOf(R.drawable.on_board1, R.drawable.on_board2, R.drawable.on_board3, R.drawable.on_board4)
    var position = position
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        binding.onBoardIv.setImageResource(onBoardImg[position])
        return binding.root
    }
}