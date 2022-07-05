package com.uni.todoary.feature.auth

import android.content.Intent
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {

        binding.loginBtnTv.setOnClickListener {
            val mIntent = Intent(this, PwLockActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
        }
        binding.loginBtnGoogleLayout.setOnClickListener {

        }
        binding.loginBtnSignInTv.setOnClickListener {

        }
    }
}