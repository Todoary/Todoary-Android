package com.uni.todoary.feature.auth.ui.view

import android.content.Intent
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.base.ApiResult
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.base.BaseDialog
import com.uni.todoary.databinding.ActivityFindPwBinding
import com.uni.todoary.feature.auth.data.module.AccountInfo
import com.uni.todoary.feature.auth.ui.viewmodel.FindPwViewModel
import com.uni.todoary.util.GMailSender
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.regex.Pattern

@AndroidEntryPoint
class FindPwActivity constructor(private var random_number : Int = 0) : BaseActivity<ActivityFindPwBinding>(ActivityFindPwBinding::inflate) {
    private val model : FindPwViewModel by viewModels()

    override fun initAfterBinding() {
        binding.findPwToolbar.toolbarBackMainTv.text = "비밀번호 재설정"
        binding.findPwToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }

        initView()
        initObserver()
    }

    private fun initView(){
        binding.findPwEmailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                model.email.value = p0.toString()
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
        binding.findPwVerificationCodeCheckTv.setOnClickListener {
            if (binding.findPwVerificationCodeEt.text.toString() == "$random_number"){
                android.app.AlertDialog.Builder(this).apply {
                    setTitle("알림")
                    setMessage("인증이 완료되었습니다.")
                    setPositiveButton("확인", null)
                    show()
                }
                model.codeChecked(0, true)
            } else {
                android.app.AlertDialog.Builder(this).apply{
                    setTitle("알림")
                    setMessage("인증코드가 일치하지 않습니다.")
                    setPositiveButton("확인", null)
                    show()
                }
                model.codeChecked(0, false)
            }
        }
        binding.findPwRebuildPwEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(curText: Editable?) {
                if(curText.toString().length >= 8){
                    model.codeChecked(2, true)
                    binding.findPwRebuildPwOffTv.visibility = View.GONE
                } else {
                    model.codeChecked(2, false)
                    binding.findPwRebuildPwOffTv.visibility = View.VISIBLE
                }
            }

        })
        binding.findPwRebuildPwCheckEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(curText: Editable?) {
                if (binding.findPwRebuildPwEt.text.toString() == curText.toString()){
                    binding.findPwRebuildPwCheckOnTv.visibility = View.VISIBLE
                    binding.findPwRebuildPwCheckOffTv.visibility = View.GONE
                    model.codeChecked(1, true)
                } else {
                    binding.findPwRebuildPwCheckOnTv.visibility = View.GONE
                    binding.findPwRebuildPwCheckOffTv.visibility = View.VISIBLE
                    model.codeChecked(1, false)
                }
            }

        })
        binding.findPwConfirmTv.setOnClickListener {
            confirmNewPw()
        }
        // 키보드에서 엔터 입력시 바로 로그인 되도록 구현
        binding.findPwRebuildPwCheckEt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    confirmNewPw()
                    hideKeyboard(binding.findPwRebuildPwCheckEt)
                    return true
                }
                return false
            }
        })
    }

    private fun confirmNewPw(){
        if (model.validationCheck()){
            val request = AccountInfo(binding.findPwEmailEt.text.toString(),
                binding.findPwRebuildPwCheckEt.text.toString())
            model.registerNewPw(request)
        } else {
            android.app.AlertDialog.Builder(this).apply {
                setTitle("알림")
                setMessage("이메일 또는 비밀번호의 유효성이 확인되지 않았습니다.")
                setPositiveButton("확인", null)
                show()
            }
        }
    }

    private fun initObserver(){
        model.findPwResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    val dialog = BaseDialog()
                    val btnData = arrayOf("네")
                    dialog.arguments = bundleOf(
                        "titleContext" to "알림",
                        "bodyContext" to "비밀번호가 성공적으로 변경되었습니다.\n" +
                                "다시 로그인 해주세요.",
                        "btnData" to btnData
                    )
                    dialog.setButtonClickListener(object: BaseDialog.OnButtonClickListener{
                        override fun onButton1Clicked() {
                            goToReLogin()
                        }
                        override fun onButton2Clicked() {

                        }
                    })
                    dialog.show(supportFragmentManager, "register_new_pw_dialog")
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        2011 -> {
                            Snackbar.make(binding.findPwConfirmTv, "존재하지 않는 유저 이메일입니다. 올바른 계정을 입력해 주세요.",
                                            Snackbar.LENGTH_SHORT).show()
                        }
                        else -> {
                            Snackbar.make(binding.findPwConfirmTv, "데이터베이스 연결에 실패했습니다.",
                                Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> {
                    Log.d("Find_Pw_Api_Error", it.message!!)
                }
            }
        })
    }

    private fun sendGmail(email : String) {
        val codebuilder = android.app.AlertDialog.Builder(this)
        val random = Random()
        random_number = random.nextInt(8999) + 1000
        GMailSender(random_number).sendEmail(email)
        //Toast.makeText(applicationContext,"보냄요",Toast.LENGTH_SHORT).show()
        codebuilder.setTitle("알림")
            .setMessage("인증코드가 메일로 발송 되었습니다.")
            .setPositiveButton("확인", null)
            .show()
    }

    private fun goToReLogin(){
        val mIntent = Intent(this, LoginActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mIntent)
    }
}