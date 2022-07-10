package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity(){
    lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingFeedback.toolbarBackMainTv.text = "문의하기"
        binding.settingFeedback.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }
    }
}
