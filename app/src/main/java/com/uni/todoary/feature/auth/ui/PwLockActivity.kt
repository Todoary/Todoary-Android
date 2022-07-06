package com.uni.todoary.feature.auth.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityPwLockBinding
import androidx.lifecycle.Observer
import com.uni.todoary.R
import com.uni.todoary.feature.main.MainActivity
import com.uni.todoary.util.getUser

class PwLockActivity : BaseActivity<ActivityPwLockBinding>(ActivityPwLockBinding::inflate) {
    private val model : PwLockViewModel by viewModels()

    override fun initAfterBinding() {
        initKeypad()
        Log.d("getUser", getUser().toString())
    }

    fun initKeypad(){
        val pwObserver = Observer<List<Int>>{ pwArr ->
            when (pwArr.size) {
                0 -> {
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                }
                1->{
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                }
                2->{
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                }
                3->{
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                }
                4->{
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_solid_btn)

                    // TODO : 현재는 더미데이터 패스워드 1234인 상태, 실제로는 유저가 정한 패스워드로 validation check 해야합니다.
                    // preferences에 저장되어 있는 유저정보의 비밀번호(secureKey)와 비교
                    if (pwArr == getUser().secureKey){
                        val mIntent = Intent(this, MainActivity::class.java)
                        startActivity(mIntent)
                        finish()
                    } else {
                        // 흔들리는 애니메이션 적용, 해당 애니메이션 시간 만큼 딜레이 하기위해 Handler 사용
                        binding.pwLockLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
                        Handler(Looper.getMainLooper()).postDelayed({
                            model.clearPw()
                        }, 300)
                    }
                }
            }
        }
        model.passward.observe(this, pwObserver)
        binding.pwLock1Tv.setOnClickListener {
            model.addPw(1)
        }
        binding.pwLock2Tv.setOnClickListener {
            model.addPw(2)
        }
        binding.pwLock3Tv.setOnClickListener {
            model.addPw(3)
        }
        binding.pwLock4Tv.setOnClickListener {
            model.addPw(4)
        }
        binding.pwLock5Tv.setOnClickListener {
            model.addPw(5)
        }
        binding.pwLock6Tv.setOnClickListener {
            model.addPw(6)
        }
        binding.pwLock7Tv.setOnClickListener {
            model.addPw(7)
        }
        binding.pwLock8Tv.setOnClickListener {
            model.addPw(8)
        }
        binding.pwLock9Tv.setOnClickListener {
            model.addPw(9)
        }
        binding.pwLock0Tv.setOnClickListener {
            model.addPw(0)
        }
        binding.pwLockBackIv.setOnClickListener {
            model.removePw()
        }
    }
}