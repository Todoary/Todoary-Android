package com.uni.todoary.feature.main.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivitySplashBinding
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.feature.auth.ui.LoginActivity
import com.uni.todoary.feature.auth.ui.PwLockActivity
import com.uni.todoary.util.*

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), LoginView {
    override fun initAfterBinding() {
        // Splash Activity 에서 자동로그인 체크 후 원래 Theme로 변경
        Handler(Looper.getMainLooper()).postDelayed({
            if(getIsAutoLogin()){
                val loginService = AuthService()
                loginService.setLoginView(this)
                val loginRequest = LoginRequest(getUser()!!.email, getUser()!!.password!!)
                loginService.autoLogin(loginRequest)
            } else {
                // 일반 로그인화면으로 이동
                val mIntent = Intent(this, LoginActivity::class.java)
                startActivity(mIntent)
                finish()
            }
            setTheme(R.style.Theme_Todoary)
        }, 300)
    }

    override fun loginLoading() {

    }

    override fun loginSuccess(result: LoginResponse) {
        // AccessToken과 RefreshToken 캐싱 (헤더에 사용할 것들)
        saveRefToken(result.token.refreshToken)
        saveXcesToken(result.token.accessToken)

        // 보안키 설정 해 두었는지 여부 확인
        if (getSecureKey() == null){
            val mIntent = Intent(this, MainActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
            finish()
        } else {
            val mIntent = Intent(this, PwLockActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
            finish()
        }
    }

    override fun loginFailure(code: Int) {
        when (code){
            2005, 2011 -> {
                Snackbar.make(binding.splashLayout, "아이디 또는 비밀번호가 틀렸습니다.", Snackbar.LENGTH_SHORT).show()
            }
            4000 -> {
                Snackbar.make(binding.splashLayout, "데이터베이스 연결에 실패하였습니다. 반복될 시 개발자에게 문의해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}