package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivitySettingBinding
import com.uni.todoary.feature.main.MainActivity

class SettingActivity : AppCompatActivity() {
    lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MainActivity::class.java)
        val alarmintent = Intent(this, AlarmActivity::class.java)
        val displayintent = Intent(this, DisplayActivity::class.java)
        val profileintent = Intent(this, ProfileActivity::class.java)
        val pwdintent = Intent(this, PasswordActivity::class.java)
        val guideintent = Intent(this, GuideActivity::class.java)
        val agreementintent = Intent(this, AgreementActivity::class.java)
        val feedbackintent = Intent(this, FeedbackActivity::class.java)
        val reviewintent = Intent(this, ReviewActivity::class.java)
        //툴바
        binding.settingToolbar.toolbarBackMainTv.text = "설정"
        binding.settingToolbar.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }

        //화면 이동
        binding.settingAlarmBtn.setOnClickListener {
            startActivity(alarmintent)
        }
        binding.settingDisplayBtn.setOnClickListener {
            startActivity(displayintent)
        }
        binding.settingProfileBtn.setOnClickListener {
            startActivity(profileintent)
        }
        binding.settingPwdBtn.setOnClickListener {
            startActivity(pwdintent)
        }
        binding.settingGuideBtn.setOnClickListener {
            startActivity(guideintent)
        }
        binding.settingAgreementBtn.setOnClickListener {
            startActivity(agreementintent)
        }
        binding.settingFeedbackBtn.setOnClickListener {
            startActivity(feedbackintent)
        }
        binding.settingReviewBtn.setOnClickListener {
            startActivity(reviewintent)
        }


    }


}