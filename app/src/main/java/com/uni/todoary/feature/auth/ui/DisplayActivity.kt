package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity(){
    lateinit var binding: ActivityDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        val fontintent = Intent(this, FontActivity::class.java)

        //툴바
        binding.settingDisplay.toolbarBackMainTv.text = "화면"
        binding.settingDisplay.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }
        binding.displayFontBtn.setOnClickListener {
            startActivity(fontintent)
        }

    }
}
