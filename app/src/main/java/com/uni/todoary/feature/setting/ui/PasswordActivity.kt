package com.uni.todoary.feature.setting.ui

import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityPwLockBinding
import com.uni.todoary.feature.auth.ui.PwLockViewModel
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser

class PasswordActivity : BaseActivity<ActivityPwLockBinding>(ActivityPwLockBinding::inflate){
    private val model : PwLockViewModel by viewModels()
    private var newPw = arrayListOf<Int>()  // 새로운 비밀번호를 체크하기 위해 임시저장하는 배열
    private lateinit var pwObserver : Observer<List<Int>>   // 처음 초기화 시키고 삭제할 옵저버(첫 암호 입력에 사용)
    override fun initAfterBinding() {
        initKeypad()    // 키패드 기능 초기화
        pwCheck()       // 기존 암호 check
    }

    fun initKeypad(){
        // depth 1 -> 기존 비밀번호 check
        pwObserver = Observer<List<Int>>{ pwArr ->
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

                    // preferences에 저장되어 있는 유저정보의 비밀번호(secureKey)와 비교
                    if (pwArr == getUser().secureKey){
                        // depth 1 -> depth 2로 진행
                        setPw()
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

    fun pwCheck(){
        binding.pwLockSubTv.text = "현재 비밀번호를 입력해 주세요"
    }

    fun setPw(){
        binding.pwLockSubTv.text = "새로운 비밀번호를 입력해 주세요"
        model.clearPw()
        model.passward.removeObserver(pwObserver)   // 기존 옵저버 제거 후 새로운 옵저버 생성 (새로운 비밀번호 생성, 체크하는 기능)
        val newPwObserver = Observer<List<Int>>{ pwArr ->
            when (pwArr.size) {
                0 -> {
                    binding.pwLockKey1.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey2.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey3.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    binding.pwLockKey4.background = ContextCompat.getDrawable(this, R.drawable.bg_login_stroke_btn)
                    if(newPw.isNotEmpty()){
                        // depth 3 -> 새로운 비밀번호 check할 때
                        binding.pwLockSubTv.text = "변경할 비밀번호를 다시 입력해 주세요"
                    }
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

                    if (newPw.size == 4 && pwArr == newPw){
                        // depth 3 에서 새로운 비밀번호 check 까지 완료 후 비밀번호 변경 완료
                        Snackbar.make(binding.pwLockSubTv, "비밀번호가 변경되었습니다.", Snackbar.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            // user 정보 업데이트 in sharedPreferences
                            val user = getUser()
                            user.secureKey!!.clear()
                            user.secureKey!!.addAll(newPw)
                            saveUser(user)
                            finish()
                        }, 1500)
                    } else {
                        // depth 2 -> 새로운 비밀번호를 입력하고 newPw에 임시저장, depth 3 에서 체크
                        if(newPw.isEmpty()){
                            newPw.addAll(model.passward.value!!)    // 깊은 복사
                            Handler(Looper.getMainLooper()).postDelayed({
                                model.clearPw()
                            }, 100)
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
        }
        model.passward.observe(this, newPwObserver)
    }
}