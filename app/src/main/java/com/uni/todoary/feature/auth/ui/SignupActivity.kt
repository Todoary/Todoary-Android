package com.uni.todoary.feature.auth.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, LoginActivity::class.java)
        //툴바
        binding.signUpToolbar.toolbarBackMainTv.text = "회원가입"
        binding.signUpToolbar.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }


        // TODO: 회원가입 기능 추가 필요
        signUpCheck()


    }


    /////////유효성 체크////////
    private fun signUpCheck(){

        ///이메일 유효성 체크///

        val emailcheck=findViewById<TextView>(R.id.signup_emailcheck_tv)
        var emailet= findViewById<EditText>(R.id.signup_email_et)
        val codecheckbtn=findViewById<Button>(R.id.sigunup_confirm_btn)
        var editor = mSharedPreferences.edit()
        val randomnum = findViewById<EditText>(R.id.signup_code_et)
        var pwet = findViewById<EditText>(R.id.signup_pw_et)
        val nextbtn = findViewById<Button>(R.id.signup_fin_btn)
        val nextintent = Intent(this, TermscheckActivity::class.java)
        var codeflag = false
        var pwflag = false
        var nickflag = false

        fun emailCheck(){
            var email = ""
            emailet.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun afterTextChanged(p0: Editable?) {
                    email = emailet.text.toString()
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        //이메일 형식이 아님
                        emailcheck.visibility = View.INVISIBLE
                        return
                    }else{
                        emailcheck.visibility = View.VISIBLE
                        return
                    }

                }
            })

        }
        fun GmailSend(){
            val sendbtn = findViewById<Button>(R.id.signup_code_btn)
            val codebuilder = android.app.AlertDialog.Builder(this)
            sendbtn.setOnClickListener {
                if(emailcheck.isVisible){
                    var email = binding.signupEmailEt.text.toString()
                    GMailSender().sendEmail(email)
                    //Toast.makeText(applicationContext,"보냄요",Toast.LENGTH_SHORT).show()
                    codebuilder.setTitle("알림")
                        .setMessage("인증코드가 메일로 발송 되었습니다.")
                        .setPositiveButton("확인",null)
                        .show()
                }
            }
        }

        codecheckbtn.setOnClickListener {
            var value = mSharedPreferences.getString("key", "데이터 없음")
            val checkbuilder = android.app.AlertDialog.Builder(this)
            editor.commit()
            //Log.d("key", "value: $value")
            if (randomnum.text.toString() == "$value") {
                //Toast.makeText(applicationContext, "인증되었습니다.", Toast.LENGTH_SHORT).show()
                checkbuilder.setTitle("알림")
                    .setMessage("인증이 완료 되었습니다.")
                    .setPositiveButton("확인",null)
                    .show()
                codeflag = true
            } else {
                //Toast.makeText(applicationContext, "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                checkbuilder.setTitle("알림")
                    .setMessage("인증코드가 일치하지 않습니다.")
                    .setPositiveButton("확인",null)
                    .show()
                codeflag = false
            }
        }

        //패스워드 format 해당하는지 체크
        //패스워드 유효성 체크(영어, 숫자 포함 8~15자리)
        fun isPasswordFormat(password:String):Boolean{
            return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}\$".toRegex())
        }
        fun PasswordCheck(){
            pwet.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var pw=pwet.text.toString()
                    if(isPasswordFormat(pw)) {
                        //Toast.makeText(applicationContext, "pw통과.", Toast.LENGTH_SHORT).show()
                        binding.signupPwcheckEt.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(p0: Editable?) {
                                if (pw != binding.signupPwcheckEt.text.toString()) {
                                    binding.signupPwcheckTv.visibility=View.VISIBLE
                                    pwflag = false
                                }else{
                                    binding.signupPwcheckTv.visibility=View.INVISIBLE
                                    pwflag = true
                                }
                            }
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            }

                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            }
                        })
                    }else{
                        //Toast.makeText(applicationContext, "pw불통.", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }


        //이름 format 해당하는지 체크
        //이름 유효성체크(1자 이상 8자 이하 영어, 한글)
        fun isNameFormat(name:String):Boolean{
            return name.matches(("^[가-힣a-zA-Z]{1,8}$").toRegex())
        }
        fun NameCheck(){
            binding.signupNameEt.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var name=binding.signupNameEt.text.toString()
                    if(isNameFormat(name)) {
                        //Toast.makeText(applicationContext, "name통과.", Toast.LENGTH_SHORT).show()
                    }else{
                        //Toast.makeText(applicationContext, "name불통.", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

        //닉네임 유효성체크 (1자 이상 10자 이하 영어, 한글, 숫자)
        fun isNicknameFormat(nickname:String):Boolean{
            return nickname.matches(("^[0-9a-zA-Z가-힣]{1,10}\$").toRegex())
        }
        fun NicknameCheck(){
            binding.signupNickEt.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var nickname=binding.signupNickEt.text.toString()
                    if(isNicknameFormat(nickname)) {
                        //Toast.makeText(applicationContext, "nickname통과.", Toast.LENGTH_SHORT).show()
                        nickflag = true
                    }else{
                        //Toast.makeText(applicationContext, "nickname불통.", Toast.LENGTH_SHORT).show()
                        nickflag = false
                    }
                }
            })

        }

        emailCheck()
        GmailSend()
        PasswordCheck()
        NameCheck()
        NicknameCheck()

        //조건 충족시 다음 화면으로
        nextbtn.setOnClickListener {
            if(nickflag) {
                startActivity(nextintent)
            }
        }

    }
}
