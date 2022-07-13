package com.uni.todoary.feature.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.uni.todoary.databinding.ActivityTermsDetailBinding

class TermsDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val backintent = Intent(this, TermscheckActivity::class.java)
        //툴바
        //binding.termsDetailToolbar.toolbarBackMainTv.text = "개인 정보 취급방침"
        binding.termsDetailToolbar.toolbarBackIv.setOnClickListener {
            startActivity(backintent)
        }

        DetailCheck()


    }

    private fun DetailCheck() {
        val detail_intent = intent
        var num = detail_intent.getStringExtra("key")
        Log.d("key: ","$num")
        if (num == "1") {
            binding.termsDetailToolbar.toolbarBackMainTv.text = "개인 정보 취급방침"
            binding.termscheckDetailTv.text="내용 1"
        }else if(num == "2"){
            binding.termsDetailToolbar.toolbarBackMainTv.text = "서비스 이용약관"
            binding.termscheckDetailTv.text="내용 2"
        }else if(num == "3"){
            binding.termsDetailToolbar.toolbarBackMainTv.text = "광고성 정보 수신"
            binding.termscheckDetailTv.text="내용 3"
        }else if(num == "4"){
            binding.termsDetailToolbar.toolbarBackMainTv.text = "위치정보 이용"
            binding.termscheckDetailTv.text="내용 4"
        }
    }
}