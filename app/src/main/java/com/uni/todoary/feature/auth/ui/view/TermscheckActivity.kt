package com.uni.todoary.feature.auth.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityTermscheckBinding
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.AgreeTermsView
import com.uni.todoary.feature.auth.ui.view.SignupActivity
import com.uni.todoary.feature.auth.ui.view.TermsDetailActivity

class TermscheckActivity : AppCompatActivity(), AgreeTermsView {
    lateinit var binding : ActivityTermscheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermscheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val allcheck = binding.termscheckAllCheck
        val nextbtn = binding.termscheckNextBtn
        val check1 = binding.termscheck1Check
        val check2 = binding.termscheck2Check
        val detail1 = binding.termscheck1Detail
        val detail2 = binding.termscheck2Detail
        val detail3 = binding.termscheck3Detail
        val mIntent = Intent(this, SignupActivity::class.java)

        //툴바
        binding.termsCheckToolbar.toolbarBackMainTv.text = "약관동의"
        binding.termsCheckToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }
        allcheck.setOnClickListener {
            AllCheck()
        }
        nextbtn.setOnClickListener {
            if(check1.isChecked==true&&check2.isChecked==true){
                mIntent.putExtra("termscheck", binding.termscheck3Check.isChecked)
                Log.d("termscheck: ",binding.termscheck3Check.isChecked.toString())
//                AgreeTerms()
                startActivity(mIntent)
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
        val spanString3 = SpannableString("마케팅 정보 수신")
        spanString3.setSpan(UnderlineSpan(),0,spanString3.length,0)
        detail3.text = spanString3

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



    }

    private fun AllCheck(){
        val allcheck = findViewById<CheckBox>(R.id.termscheck_all_check)
        val check1 = binding.termscheck1Check
        val check2 = binding.termscheck2Check
        val check3 = binding.termscheck3Check
        if(allcheck.isChecked){
            check1.isChecked = true
            check2.isChecked = true
            check3.isChecked = true
        }

    }

//    private fun AgreeTerms(){
//        val AgreeTermsService = AuthService()
//        AgreeTermsService.setAgreeTermsView(this)
//        val isChecked = binding.termscheck3Check.isChecked
//        Log.d("isChecked",isChecked.toString())
//        AgreeTermsService.AgreeTerms(isChecked)
//        }

    override fun AgreeTermsLoading() {
    }

    override fun AgreeTermsSuccess() {
        Log.d("마케팅동의","성공")
    }

    override fun AgreeTermsFailure(code: Int) {
        Log.d("마케팅동의실패",code.toString())
    }

}




