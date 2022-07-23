package com.uni.todoary.feature.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.setting.ui.SettingActivity
import com.uni.todoary.util.getRefToken
import com.uni.todoary.util.getXcesToken

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        binding.mainMenuIv.setOnClickListener {
            val menuIntent = Intent(this, SettingActivity::class.java)
            startActivity(menuIntent)
        }

        Log.d("asdf", getRefToken()!!)
        Log.d("asdfaxces", getXcesToken()!!)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment())
            .commit()
    }
}