package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityAgreementBinding
import com.uni.todoary.feature.auth.ui.view.TermsDetailActivity

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
            finish()
        }


        binding.agreementInformLl.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","1")
            startActivity(detail_intent)

        }
        binding.agreementServiceLl.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","2")
            startActivity(detail_intent)
        }
        binding.agreementAdLl.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","3")
            startActivity(detail_intent)
        }


    }
}
