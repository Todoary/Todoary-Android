package com.uni.todoary.feature.auth.ui.view

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityFindPwBinding
import com.uni.todoary.feature.auth.data.module.AccountInfo
import com.uni.todoary.feature.auth.ui.viewmodel.FindPwViewModel
import com.uni.todoary.util.GMailSender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwActivity : BaseActivity<ActivityFindPwBinding>(ActivityFindPwBinding::inflate) {
    private val model : FindPwViewModel by viewModels()

    override fun initAfterBinding() {
        binding.findPwToolbar.toolbarBackMainTv.text = "비밀번호 재설정"
        binding.findPwToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }

        initView()

    }

    private fun initView(){
        binding.findPwEmailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                model.email.value = p0.toString()
                //ExistenceCheck()
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    //이메일 형식이 아님
//                    emailcheck.visibility = View.INVISIBLE
//                    return
//                } else {
//                    emailcheck.visibility = View.VISIBLE
//                    return
//                }
            }
        })
        binding.findPwIdCheckTv.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(model.email.value as CharSequence).matches()) {
                //이메일 형식이 아님
                binding.findPwEmailErrorTv.visibility = View.VISIBLE
                binding.findPwIdHintTv.visibility = View.INVISIBLE
            } else {
                binding.findPwEmailErrorTv.visibility = View.INVISIBLE
                binding.findPwIdHintTv.visibility = View.VISIBLE
                sendGmail(binding.findPwEmailEt.text.toString())
            }
        }
    }

    private fun sendGmail(email : String) {
        val codebuilder = android.app.AlertDialog.Builder(this)
        val randomNumber = (1000..9999).random()
        GMailSender(randomNumber).sendEmail(email)
        //Toast.makeText(applicationContext,"보냄요",Toast.LENGTH_SHORT).show()
        codebuilder.setTitle("알림")
            .setMessage("인증코드가 메일로 발송 되었습니다.")
            .setPositiveButton("확인", null)
            .show()
    }
}