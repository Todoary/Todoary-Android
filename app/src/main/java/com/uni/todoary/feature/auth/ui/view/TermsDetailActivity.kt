package com.uni.todoary.feature.auth.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityTermsDetailBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.AgreeTermsView

class TermsDetailActivity : AppCompatActivity(), AgreeTermsView {

    lateinit var binding : ActivityTermsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //툴바
        //binding.termsDetailToolbar.toolbarBackMainTv.text = "개인 정보 취급방침"
        binding.termsDetailToolbar.toolbarBackIv.setOnClickListener {
            finish()
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
            binding.termsDetailToolbar.toolbarBackMainTv.text = "마케팅 정보 수신"
            binding.termscheckDetailTv.text=getString(R.string.terms_check_detail)
        }
        else if(num == "4"){
            binding.termsDetailToolbar.toolbarBackMainTv.text = "마케팅 정보 수신"
            binding.termscheckDetailTv.text=getString(R.string.terms_check_detail)
            binding.termsCheckllLl.visibility= View.VISIBLE

        }
        AgreeTerms()
    }



    private fun AgreeTerms() {
        var isChecked = binding.termsCheckboxCb.isChecked
        val agreeTermsService = AuthService()
        agreeTermsService.setAgreeTermsView(this)
        agreeTermsService.AgreeTerms(isChecked)

    }
    override fun AgreeTermsLoading() {
    }

    override fun AgreeTermsSuccess() {
        Log.d("마케팅동의","성공")
    }

    override fun AgreeTermsFailure(code: Int) {
        Log.d("마케팅동의실패",code.toString())
    }
}