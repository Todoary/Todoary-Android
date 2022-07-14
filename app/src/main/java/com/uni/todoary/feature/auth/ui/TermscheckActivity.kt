package com.uni.todoary.feature.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.CheckBox
import android.widget.Toast
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityTermscheckBinding
import com.uni.todoary.feature.setting.ui.FeedbackActivity

class TermscheckActivity : AppCompatActivity() {
    lateinit var binding : ActivityTermscheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermscheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backintent = Intent(this, SignupActivity::class.java)
        val allcheck = binding.termscheckAllCheck
        val nextbtn = binding.termscheckNextBtn
        val check1 = binding.termscheck1Check
        val check2 = binding.termscheck2Check
        val check3 = binding.termscheck3Check
        val check4 = binding.termscheck4Check
        val detail1 = binding.termscheck1Detail
        val detail2 = binding.termscheck2Detail
        val detail3 = binding.termscheck3Detail
        val detail4 = binding.termscheck4Detail
        //val detailintent=Intent(this, TermsDetailActivity::class.java)

        //툴바
        binding.termsCheckToolbar.toolbarBackMainTv.text = "약관동의"
        binding.termsCheckToolbar.toolbarBackIv.setOnClickListener {
            startActivity(backintent)
        }
        allcheck.setOnClickListener {
            AllCheck()
        }
        nextbtn.setOnClickListener {
            if(check1.isChecked==true&&check2.isChecked==true){
                Toast.makeText(applicationContext, "다 체크됨",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "체크부터 해줘 ",Toast.LENGTH_SHORT).show()
            }
        }

        val spanString1 = SpannableString("개인 정보 취급방침")
        spanString1.setSpan(UnderlineSpan(),0,spanString1.length,0)
        detail1.text = spanString1
        val spanString2 = SpannableString("서비스 이용약관")
        spanString2.setSpan(UnderlineSpan(),0,spanString2.length,0)
        detail2.text = spanString2
        val spanString3 = SpannableString("광고성 정보 수신")
        spanString3.setSpan(UnderlineSpan(),0,spanString3.length,0)
        detail3.text = spanString3
        val spanString4 = SpannableString("위치정보 이용")
        spanString4.setSpan(UnderlineSpan(),0,spanString4.length,0)
        detail4.text = spanString4


        detail1.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","1")
            startActivity(detail_intent)

        }
        detail2.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","2")
            startActivity(detail_intent)
        }
        detail3.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","3")
            startActivity(detail_intent)
        }
        detail4.setOnClickListener {
            val detail_intent = Intent(this, TermsDetailActivity::class.java)
            detail_intent.putExtra("key","4")
            startActivity(detail_intent)
        }



    }

    private fun AllCheck(){
        val allcheck = findViewById<CheckBox>(R.id.termscheck_all_check)
        val check1 = binding.termscheck1Check
        val check2 = binding.termscheck2Check
        val check3 = binding.termscheck3Check
        val check4 = binding.termscheck4Check
        if(allcheck.isChecked){
            check1.isChecked = true
            check2.isChecked = true
            check3.isChecked = true
            check4.isChecked = true
        }

    }
}