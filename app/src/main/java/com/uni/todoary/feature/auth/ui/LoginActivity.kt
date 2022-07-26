package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.view.KeyEvent
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityLoginBinding
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.main.ui.MainActivity
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {


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
            if (binding.loginAutoCheckBox.isChecked) {
                // TODO : 자동로그인 구현 (어떻게??) -> 로그인 플로우 필요
                //  여기선 자동로그인 상태 저장만 해두고, 스플래시에서 자동로그인 체크 후 이쪽으로 아예 넘어오면 안됨
                //  sharedPreferences?

            } else {

            }
            login()
        }
        binding.loginBtnGoogleLayout.setOnClickListener {
            // TODO : 아래부분은 더미데이터 User 삽입하는 부분 추후 지우고 소셜로그인 구현
            val userData = User("King현석", "MuYaHo", "hyuns6677@gmail.com", "madpotato0606", arrayListOf(3,6,9,5))
            saveUser(userData)
        }
        binding.loginBtnSignInTv.setOnClickListener {
            val mIntent = Intent(this, TermscheckActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
        }
        binding.loginForgotPwTv.setOnClickListener {
            val mIntent = Intent(this, FindPwActivity::class.java)
            startActivity(mIntent)
        }
    }

    // 아이디 패스워드 sharedPreferences에서 확인 후 맞으면 로그인, 틀리면 애니메이션 & 안내메시지
    private fun login(){
        if (binding.loginIdEt.text.toString() == getUser().email &&
            binding.loginPwEt.text.toString() == getUser().password) {
            val mIntent = Intent(this, PwLockActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mIntent)
            finish()
        } else {
            binding.loginPwEt.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
            binding.loginIdEt.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
            Snackbar.make(binding.loginBtnTv, "아이디 또는 비밀번호가 틀렸습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }
}