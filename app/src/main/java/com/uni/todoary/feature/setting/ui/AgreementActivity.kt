package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity(){
    lateinit var binding: ActivityAgreementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingAgreement.toolbarBackMainTv.text = "약관 및 정책"
        binding.settingAgreement.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }
    }
}
