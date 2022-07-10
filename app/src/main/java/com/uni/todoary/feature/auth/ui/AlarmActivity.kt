package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity(){
    lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingAlarmToolbar.toolbarBackMainTv.text = "알림"
        binding.settingAlarmToolbar.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }
    }
}
