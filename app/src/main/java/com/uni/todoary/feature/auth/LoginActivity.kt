package com.uni.todoary.feature.auth

import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {

        binding.loginBtnTv.setOnClickListener {

        }
        binding.loginBtnGoogleLayout.setOnClickListener {

        }
        binding.loginBtnSignInTv.setOnClickListener {

        }
    }
}