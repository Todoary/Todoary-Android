package com.uni.todoary.feature.auth.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivitySignupBinding
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.EmailCheckView
import com.uni.todoary.feature.auth.data.view.ExistenceCheckView
import com.uni.todoary.feature.auth.data.view.SignInView
import com.uni.todoary.util.GMailSender

class SignupActivity : AppCompatActivity(), SignInView, EmailCheckView, ExistenceCheckView {
    lateinit var binding: ActivitySignupBinding
    var codeflag = false
    var pwflag1 = false
    var nickflag = false
    var nameflag = false
    var emailflag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, LoginActivity::class.java)
        //툴바
        binding.signUpToolbar.toolbarBackMainTv.text = "회원가입"


        binding.signUpToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }

        signUpCheck()
        binding.signupFinBtn.setOnClickListener {
            //Log.d("되니2", fin_flag.toString())
            Log.d("닉넴", nickflag.toString())
            Log.d("코드",codeflag.toString())
            Log.d("이름", nameflag.toString())
            Log.d("비번", pwflag1.toString())
            if (nickflag && codeflag && nameflag && pwflag1) {
                Signin()
            }

        }


    }


    /////////유효성 체크////////
    private fun signUpCheck() {

        val emailcheck = findViewById<TextView>(R.id.signup_emailcheck_tv)
        var emailet = findViewById<EditText>(R.id.signup_email_et)
        val codecheckbtn = findViewById<Button>(R.id.sigunup_confirm_btn)
        var editor = mSharedPreferences.edit()
        val randomnum = findViewById<EditText>(R.id.signup_code_et)
        var pwet = findViewById<EditText>(R.id.signup_pw_et)

        ///이메일 유효성 체크///
        fun emailCheck() {
            var email = ""
            emailet.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    email = emailet.text.toString()
                    //ExistenceCheck()
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        //이메일 형식이 아님
                        emailcheck.visibility = View.INVISIBLE
                        return
                    } else {
                        emailcheck.visibility = View.VISIBLE
                        return
                    }

                }
            })
        }


        //패스워드 format 해당하는지 체크
        //패스워드 유효성 체크(영어, 숫자 포함 8~15자리)
        fun isPasswordFormat(password: String): Boolean {
            return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}\$".toRegex())
        }

        fun PasswordCheck() {
            pwet.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var pw = pwet.text.toString()
                    if (isPasswordFormat(pw)) {
                        //Toast.makeText(applicationContext, "pw통과.", Toast.LENGTH_SHORT).show()
                        binding.signupPwcheckEt.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(p0: Editable?) {
                            }

                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (pw != binding.signupPwcheckEt.text.toString()) {
                                    binding.signupPwcheckTv.visibility = View.VISIBLE
                                    pwflag1 = false
                                } else {
                                    binding.signupPwcheckTv.visibility = View.INVISIBLE
                                    pwflag1 = true
                                }
                            }
                        })
                    } else {
                        //Toast.makeText(applicationContext, "pw불통.", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("비번1", pwflag1.toString())
                }
            })

            binding.signupPwcheckEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var pw = binding.signupPwcheckEt.text.toString()
                    if (isPasswordFormat(pw)) {
                        //Toast.makeText(applicationContext, "pw통과.", Toast.LENGTH_SHORT).show()
                        binding.signupPwEt.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(p0: Editable?) {
                            }

                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (pw != binding.signupPwEt.text.toString()) {
                                    binding.signupPwcheckTv.visibility = View.VISIBLE
                                    pwflag1 = false
                                } else {
                                    binding.signupPwcheckTv.visibility = View.INVISIBLE
                                    pwflag1 = true
                                }
                            }
                        })
                    } else {
                        //Toast.makeText(applicationContext, "pw불통.", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("비번2", pwflag1.toString())
                }
            })

        }


        //이름 format 해당하는지 체크
        //이름 유효성체크(1자 이상 8자 이하 영어, 한글)
        fun isNameFormat(name: String): Boolean {
            return name.matches(("^[가-힣a-zA-Z]{1,8}$").toRegex())
        }

        fun NameCheck() {
            binding.signupNameEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var name = binding.signupNameEt.text.toString()
                    nameflag = isNameFormat(name)
                    Log.d("이름", nameflag.toString())
                }
            })

        }

        //닉네임 유효성체크 (1자 이상 10자 이하 영어, 한글, 숫자)
        fun isNicknameFormat(nickname: String): Boolean {
            return nickname.matches(("^[0-9a-zA-Z가-힣]{1,10}\$").toRegex())
        }

        fun NicknameCheck() {
            binding.signupNickEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var nickname = binding.signupNickEt.text.toString()
                    if (isNicknameFormat(nickname)) {
                        //Toast.makeText(applicationContext, "nickname통과.", Toast.LENGTH_SHORT).show()
                        nickflag = true
                    } else {
                        //Toast.makeText(applicationContext, "nickname불통.", Toast.LENGTH_SHORT).show()
                        nickflag = false
                    }
                    Log.d("닉넴", nickflag.toString())
                }
            })
        }

        emailCheck()
        DuplicateCheck()

        codecheckbtn.setOnClickListener {
            var value = mSharedPreferences.getString("key", "데이터 없음")
            val checkbuilder = android.app.AlertDialog.Builder(this)
            editor.commit()
            //Log.d("key", "value: $value")
            if (randomnum.text.toString() == "$value") {
                //Toast.makeText(applicationContext, "인증되었습니다.", Toast.LENGTH_SHORT).show()
                checkbuilder.setTitle("알림")
                    .setMessage("인증이 완료 되었습니다.")
                    .setPositiveButton("확인", null)
                    .show()
                codeflag = true
                Log.d("이메일", codeflag.toString())
            } else {
                //Toast.makeText(applicationContext, "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                checkbuilder.setTitle("알림")
                    .setMessage("인증코드가 일치하지 않습니다.")
                    .setPositiveButton("확인", null)
                    .show()
                codeflag = false
                Log.d("이메일", codeflag.toString())
            }

        }

        PasswordCheck()
        NameCheck()
        NicknameCheck()

}

    private fun DuplicateCheck(){
        val sendbtn = findViewById<Button>(R.id.signup_code_btn)
        sendbtn.setOnClickListener {
            if (binding.signupEmailcheckTv.isVisible){
                EmailCheck()
            }

        }

    }
    private fun GmailSend() {
        val codebuilder = android.app.AlertDialog.Builder(this)
        Log.d("중복된 이메일","아님")
                var email = binding.signupEmailEt.text.toString()
                GMailSender().sendEmail(email)
                //Toast.makeText(applicationContext,"보냄요",Toast.LENGTH_SHORT).show()
                codebuilder.setTitle("알림")
                    .setMessage("인증코드가 메일로 발송 되었습니다.")
                    .setPositiveButton("확인", null)
                    .show()
    }


    private fun Signin() {
        val signinService = AuthService()
        signinService.setSignInView(this)
        if (nickflag && codeflag && nameflag && pwflag1){
            val email = binding.signupEmailEt.text.toString()
            val password = binding.signupPwEt.text.toString()
            val name = binding.signupNameEt.text.toString()
            val nickname = binding.signupNickEt.text.toString()
            val isTermsEnable = intent.getBooleanExtra("termscheck",false)
            Log.d("isTermsEnable",isTermsEnable.toString())
            val request = SignInRequest(email, name,nickname,password,isTermsEnable)
            signinService.SignIn(request)
        }
    }

    override fun SignInLoading() {
    }

    override fun SignInSuccess() {
        //조건 충족시 다음 화면으로
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun SignInFailure(code: Int) {
        Toast.makeText(this, "회원가입 실패",Toast.LENGTH_SHORT).show()
        Log.d("Error",code.toString())
    }

    private fun EmailCheck() {
        val email = binding.signupEmailEt.text.toString()
        val emailcheckService = AuthService()
        emailcheckService.setEmailCheckView(this)
        emailcheckService.EmailCheck(email)

    }

    override fun EmailCheckSuccess() {
        GmailSend()
        Toast.makeText(this, "사용가능한 이메일",Toast.LENGTH_SHORT).show()
    }

    override fun EmailCheckFailure(code: Int) {
        Toast.makeText(this, "중복된 이메일을 입력했습니다.",Toast.LENGTH_SHORT).show()
        Log.d("중복된 이메일","임")
    }

    override fun EmailCheckLoading() {
    }

    private fun ExistenceCheck() {
        var email = binding.signupEmailEt.text.toString()
        val existencecheckService = AuthService()
        existencecheckService.setExistenceCheckView(this)
//        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            existencecheckService.ExistenceCheck(email)
//        }
        existencecheckService.ExistenceCheck(email)

    }

    override fun ExistenceCheckLoading() {
    }

    override fun ExistenceCheckSuccess() {
        //binding.signupEmailcheckTv.visibility = View.VISIBLE
        Log.d("이메일 존재여부","있음")
        return
    }

    override fun ExistenceCheckFailure(code: Int) {
        //binding.signupEmailcheckTv.visibility = View.INVISIBLE
        Log.d("이메일 존재여부","없음")
        return
    }
}