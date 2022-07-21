package com.uni.todoary.feature.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.setting.ui.SettingActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        binding.mainMenuIv.setOnClickListener {
            val menuIntent = Intent(this, SettingActivity::class.java)
            startActivity(menuIntent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment())
            .commit()
    }
}