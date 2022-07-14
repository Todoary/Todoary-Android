package com.uni.todoary.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.setting.ui.SettingActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn = binding.mainSettingBtn
        val intent = Intent(this, SettingActivity::class.java)
        btn.setOnClickListener {
            startActivity(intent)
        }
    }
}