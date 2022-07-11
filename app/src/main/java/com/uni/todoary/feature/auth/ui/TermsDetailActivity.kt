package com.uni.todoary.feature.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uni.todoary.databinding.ActivityTermsDetailBinding

class TermsDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val backintent = Intent(this, TermscheckActivity::class.java)
        //툴바
        binding.termsDetailToolbar.toolbarBackMainTv.text = "개인 정보 취급방침"
        binding.termsDetailToolbar.toolbarBackIv.setOnClickListener {
            startActivity(backintent)
        }

    }
}