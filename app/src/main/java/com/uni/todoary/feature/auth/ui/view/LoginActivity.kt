package com.uni.todoary.feature.auth.ui.view

import android.content.Intent
import android.net.Uri
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
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.main.ui.view.MainActivity
import com.uni.todoary.util.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.view.GetProfileView
import com.uni.todoary.feature.auth.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.uni.todoary.feature.auth.data.module.SocialLoginRequest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), GetProfileView {
    lateinit var binding : ActivityLoginBinding
    private val loginModel : LoginViewModel by viewModels()
    val G_SIGN_IN : Int = 1
    private var GOOGLE_LOGIN_CODE = 9001
    val RC_SIGN_IN =2
    lateinit var mGoogleSignInClient : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            // loginModel.socialLogin()
            // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
            // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .requestIdToken("666947728476-hjt48pj7805frhiotfon0mv8u9gs80ca.apps.googleusercontent.com")
                .build()
            Log.d("gsgsoo", gso.toString())

            // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
            mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)

            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            // startActivityForResult(signInIntent, RC_SIGN_IN)
            startForResult.launch(signInIntent)
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

        addObservers()
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result : ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val data : Intent = result.data!!
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val acct: GoogleSignInAccount = task.getResult(ApiException::class.java)
                    if (task.isSuccessful) {
                        val personName = acct.displayName.toString()
                        val personEmail = acct.email.toString()
                        val personId = acct.id.toString()
                        val idToken = acct.idToken
                        val loginRequest = SocialLoginRequest(personEmail, personName, personId)
                        Log.d("hehelloo", loginRequest.toString())
                        loginModel.socialLogin(loginRequest)
                    }
                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    // Please refer to theaut GoogleSignInStatusCodes class reference for more information.
                    Log.e("Google_Account_Info", "signInResult:failed code=" + e.statusCode)
                }
            }
        }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val acct: GoogleSignInAccount = task.getResult(ApiException::class.java)
//                if (task.isSuccessful) {
//                    val personName = acct.displayName
//                    val personGivenName = acct.givenName
//                    val personFamilyName = acct.familyName
//                    val personEmail = acct.email
//                    val personId = acct.id
//                    val personPhoto: Uri? = acct.photoUrl
//                    Log.d("Google_Account_Info", "handleSignInResult:personName $personName")
//                    Log.d("Google_Account_Info", "handleSignInResult:personGivenName $personGivenName")
//                    Log.d("Google_Account_Info", "handleSignInResult:personEmail $personEmail")
//                    Log.d("Google_Account_Info", "handleSignInResult:personId $personId")
//                    Log.d("Google_Account_Info", "handleSignInResult:personFamilyName $personFamilyName")
//                    Log.d("Google_Account_Info", "handleSignInResult:personPhoto $personPhoto")
//                }
//            } catch (e: ApiException) {
//                // The ApiException status code indicates the detailed failure reason.
//                // Please refer to the GoogleSignInStatusCodes class reference for more information.
//                Log.e("Google_Account_Info", "signInResult:failed code=" + e.statusCode)
//            }
//        }
//    }

    private fun addObservers(){
        loginModel.login_resp.observe(this, {
            when (it.status){
                ApiResult.Status.SUCCESS -> {
                    // 유저 정보 가져와서 캐싱
                    loginModel.getUserProfile(binding.loginPwEt.text.toString())
                }
                ApiResult.Status.LOADING -> {

                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        2005, 2011 -> {
                            binding.loginPwEt.startAnimation(AnimationUtils.loadAnimation(this, com.uni.todoary.R.anim.shake))
                            binding.loginIdEt.startAnimation(AnimationUtils.loadAnimation(this, com.uni.todoary.R.anim.shake))
                            Snackbar.make(binding.loginBtnTv, "아이디 또는 비밀번호가 틀렸습니다.", Snackbar.LENGTH_SHORT).show()
                        }
                        2112 -> {
                            binding.loginPwEt.startAnimation(AnimationUtils.loadAnimation(this, com.uni.todoary.R.anim.shake))
                            Snackbar.make(binding.loginBtnTv, "비밀번호를 확인해 주세요.", Snackbar.LENGTH_SHORT).show()
                        }
                        2012 -> {
                            Snackbar.make(binding.loginBtnTv, "정지된 계정입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                        4000 -> {
                            Snackbar.make(binding.loginBtnTv, "데이터베이스 연결에 실패하였습니다. 반복될 시 개발자에게 문의해 주세요.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> {
                    Toast.makeText(this, "인터넷 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("Login_Api_Network_Error", it.message!!)
                }
            }
        })      // 로그인 Response 옵저버
        loginModel.isProfileSuccess.observe(this, {
            if (it){
                // 자동 로그인 여부 캐싱
                if (binding.loginAutoCheckBox.isChecked){
                    loginModel.saveIsAutoLogin(true)
                } else {
                    loginModel.saveIsAutoLogin(false)
                }
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
            } else {
                Toast.makeText(this, "제대로된 유저 정보를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })   // 유저정보 가져오는 API 통신
        loginModel.socialLogin_resp.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    if (it.data!!.isNewUser){
                        Toast.makeText(this, "새로운놈", Toast.LENGTH_SHORT).show()
                    } else {
                        // 기존유저일 경우 토큰만 받아와서 자동로그인으로 전환
                        Toast.makeText(this, it.data!!.token.toString(), Toast.LENGTH_SHORT).show()
                        loginModel.saveIsAutoLogin(true)
                        loginModel.saveTokens(it.data.token)
                        loginModel.getUserProfile("null")
                    }
                }
                ApiResult.Status.API_ERROR -> {}
                ApiResult.Status.NETWORK_ERROR ->{}
            }
        })
    }

    // 아이디 패스워드 sharedPreferences에서 확인 후 맞으면 로그인, 틀리면 애니메이션 & 안내메시지
    private fun login() {
        // 로그인 API 호출
        val request =
            LoginRequest(binding.loginIdEt.text.toString(), binding.loginPwEt.text.toString())
        loginModel.login(request)
    }

    fun hideKeyboard(v: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun getProfileLoading() {

    }

    override fun getProfileSuccess(result: User) {
        result.password = binding.loginPwEt.text.toString()
        Log.d("user", result.toString())
        saveUser(result)
    }

    override fun getProfileFailure(code: Int) {
        when (code){
            2010, 4000 -> {
                Toast.makeText(this, "제대로 된 유저 정보를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}