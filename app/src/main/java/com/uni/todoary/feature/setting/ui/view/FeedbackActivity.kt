package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            finish()
        }

        //인스타그램 이동
        val insta_intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/todoary_official"))
        binding.feedbackInstaLl.setOnClickListener {
            startActivity(insta_intent)
        }
        binding.feedbackEmailLl.setOnClickListener {
            val layoutInflater = LayoutInflater.from(this)
            val view = layoutInflater.inflate(R.layout.email_dialog,null)

            val builder = AlertDialog.Builder(this)
                .setView(view)
                .create()


            val bugbtn=view.findViewById<LinearLayout>(R.id.email_bug_ll)
            val profilebtn=view.findViewById<LinearLayout>(R.id.email_profile_ll)
            val feedbackbtn=view.findViewById<LinearLayout>(R.id.email_feedback_ll)
            val addbtn=view.findViewById<LinearLayout>(R.id.email_add_ll)
            val etcbtn=view.findViewById<LinearLayout>(R.id.email_etc_ll)

            bugbtn.setOnClickListener {
                sendEmail("[버그신고]")
                builder.dismiss()
            }
            profilebtn.setOnClickListener {
                sendEmail("[계정문의]")
                builder.dismiss()
            }
            feedbackbtn.setOnClickListener {
                sendEmail("[피드백]")
                builder.dismiss()
            }
            addbtn.setOnClickListener {
                sendEmail("[기능추가]")
                builder.dismiss()
            }
            etcbtn.setOnClickListener {
                sendEmail("[기타]")
                builder.dismiss()
            }

            builder.show()
            builder.window?.setLayout(800,WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun sendEmail(category:String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        //mIntent.data=Uri.parse("mailto:")
        mIntent.type="text/plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("todoary.app@gmail.com"))
        mIntent.putExtra(Intent.EXTRA_SUBJECT,category)
        mIntent.putExtra(Intent.EXTRA_TEXT,"문의 내용을 입력해 주세요.")
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