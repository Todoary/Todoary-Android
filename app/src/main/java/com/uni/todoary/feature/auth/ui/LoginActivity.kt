package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.databinding.ActivityLoginBinding
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.feature.main.ui.MainActivity
import com.uni.todoary.util.*
import android.widget.Toast
import androidx.activity.viewModels
import com.uni.todoary.feature.auth.data.view.GetProfileView
import java.util.*


class LoginActivity : AppCompatActivity(), LoginView, GetProfileView {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO : 로그인 생략버튼, 배포시엔 삭제
        binding.loginExitBtn.setOnClickListener {
            val exitIntent = Intent(this, MainActivity::class.java)
            startActivity(exitIntent)
        }

        // 키보드에서 엔터 입력시 바로 로그인 되도록 구현
        binding.loginPwEt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    login()
                    hideKeyboard(binding.loginPwEt)
                    return true
                }
                return false
            }
        })

        binding.loginBtnTv.setOnClickListener {
            login()
        }
        binding.loginBtnGoogleLayout.setOnClickListener {
            // TODO : 아래부분은 더미데이터 User 삽입하는 부분 추후 지우고 소셜로그인 구현
            val userData = User(null, "규규>.<", "귀여운 규규얌 쀼쀼쀼", "hyuns6677@gmail.com", "madpotato0606")
            saveUser(userData)
        }
        binding.loginBtnSignInTv.setOnClickListener {
            val mIntent = Intent(this, SignupActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
        }
        binding.loginForgotPwTv.setOnClickListener {
            val mIntent = Intent(this, FindPwActivity::class.java)
            startActivity(mIntent)
        }
    }

    // 아이디 패스워드 sharedPreferences에서 확인 후 맞으면 로그인, 틀리면 애니메이션 & 안내메시지
    private fun login() {
        // 로그인 API 호출
        val loginService = AuthService()
        loginService.setLoginView(this)
        val request =
            LoginRequest(binding.loginIdEt.text.toString(), binding.loginPwEt.text.toString())
            loginService.logIn(request)
    }

    fun hideKeyboard(v: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun loginLoading() {

    }

    override fun loginSuccess(result: LoginResponse) {
        // AccessToken과 RefreshToken 캐싱 (헤더에 사용할 것들)
        saveRefToken(result.token.refreshToken)
        saveXcesToken(result.token.accessToken)

        // 유저 정보 가져와서 캐싱
        val service = AuthService()
        service.setProfileView(this)
        service.getProfile()

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
                binding.loginPwEt.startAnimation(AnimationUtils.loadAnimation(this, com.uni.todoary.R.anim.shake))
                binding.loginIdEt.startAnimation(AnimationUtils.loadAnimation(this, com.uni.todoary.R.anim.shake))
                Snackbar.make(binding.loginBtnTv, "아이디 또는 비밀번호가 틀렸습니다.", Snackbar.LENGTH_SHORT).show()
            }
            4000 -> {
                Snackbar.make(binding.loginBtnTv, "데이터베이스 연결에 실패하였습니다. 반복될 시 개발자에게 문의해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun getProfileLoading() {

    }

    override fun getProfileSuccess(result: User) {
        result.password = binding.loginPwEt.text.toString()
        Log.d("user", result.toString())
        saveUser(result)

        // 자동 로그인 여부 캐싱
        if (binding.loginAutoCheckBox.isChecked){
            saveIsAutoLogin(true)
        } else {
            saveIsAutoLogin(false)
        }

    }

    override fun getProfileFailure(code: Int) {
        when (code){
            2010, 4000 -> {
                Toast.makeText(this, "제대로 된 유저 정보를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}