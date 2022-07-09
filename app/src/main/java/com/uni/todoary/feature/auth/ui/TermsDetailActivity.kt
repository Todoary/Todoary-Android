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

        val backbtn = binding.termsdetailBackBtn
        val backintent = Intent(this, TermscheckActivity::class.java)
        backbtn.setOnClickListener{
            startActivity(backintent)
        }
    }
}