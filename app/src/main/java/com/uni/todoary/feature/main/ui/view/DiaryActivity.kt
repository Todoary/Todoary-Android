package com.uni.todoary.feature.main.ui.view

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityDiaryBinding
import com.uni.todoary.util.DiaryAdapter

class DiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리사이클러뷰 연결

        var recyclerView = binding.diaryRecyclerRv

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        var adapter = DiaryAdapter()
        recyclerView.adapter=adapter


        binding.diaryBackIv.setOnClickListener {
            finish()
        }
        binding.diaryCompleteTv.setOnClickListener {
            diaryAdd()
        }
    }

    private fun diaryAdd() {
        TODO("Not yet implemented")
    }
}