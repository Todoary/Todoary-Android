package com.uni.todoary.feature.auth.ui

import android.content.Intent
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityLoginBinding
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.util.saveUser

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {

        binding.loginBtnTv.setOnClickListener {
            val mIntent = Intent(this, PwLockActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
        }
        binding.loginBtnGoogleLayout.setOnClickListener {
            // TODO : 아래부분은 더미데이터 User 삽입하는 부분 추후 지우고 소셜로그인 구현
            val userData = User("King현석", "hyuns6677@gmail.com", "madpotato0606", arrayListOf(3,6,9,5))
            saveUser(userData)
        }
        binding.loginBtnSignInTv.setOnClickListener {
            val mIntent = Intent(this, SignupActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
        }
    }
}