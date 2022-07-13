package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity(){
    lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingFeedback.toolbarBackMainTv.text = "문의하기"
        binding.settingFeedback.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }

        //인스타그램 이동
        val insta_intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/todoary_official"))
        binding.feedbackInstaLl.setOnClickListener {
            startActivity(insta_intent)
        }
        binding.feedbackEmailLl.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val mIntent = Intent(Intent.ACTION_SEND)
        //mIntent.data=Uri.parse("mailto:")
        mIntent.type="text/plain"

        //받는 이가 자동 설정 안됨
        mIntent.putExtra(Intent.EXTRA_EMAIL, "dlawotn321@gmail.com")
        mIntent.putExtra(Intent.EXTRA_SUBJECT,"<테스트>")
        mIntent.putExtra(Intent.EXTRA_TEXT,"테스트 내용")
        mIntent.type="message/rfc822"

        try{
            //start email intent
            startActivity(mIntent)
        } catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}