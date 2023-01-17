package com.uni.todoary.feature.auth.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.config.Tokens
import com.uni.todoary.feature.auth.data.module.*
import com.uni.todoary.feature.auth.data.repository.LoginRepository
import com.uni.todoary.util.getUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    ) : ViewModel() {

    private val _login_resp = MutableLiveData<ApiResult<LoginResponse>>()
    val login_resp : MutableLiveData<ApiResult<LoginResponse>>
        get() =_login_resp

    private val _isProfileSuccess = MutableLiveData<Boolean>()
    val isProfileSuccess : MutableLiveData<Boolean>
        get() =_isProfileSuccess

    private val _socialLogin_resp = MutableLiveData<ApiResult<SocialLoginResponse>>()
    val socialLogin_resp : MutableLiveData<ApiResult<SocialLoginResponse>>
        get() =_socialLogin_resp

    private val _socialSignin_resp = MutableLiveData<ApiResult<Any>>()
    val socialSignin_resp : MutableLiveData<ApiResult<Any>>
        get() =_socialSignin_resp

    private val socialSigninRequest = MutableLiveData<SocialSignInRequest>()

    fun setSocialSigninRequest(name : String, email : String, provider : String, providerId : String,
                               isTermsEnable : Boolean){
        this.socialSigninRequest.value = SocialSignInRequest(
            name, email, provider, providerId, isTermsEnable
        )
    }

    fun login(request : LoginRequest) {
        viewModelScope.launch {
            _login_resp.value = ApiResult.loading()
            repository.login(request).let{
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        // 엑세스 토큰만 저장
                        repository.saveXcesToken(it.body()!!.result!!.token.accessToken)
                        Log.d("token", it.body()!!.result!!.token.accessToken)
                        _login_resp.value = ApiResult.success(it.body()!!.result)
                    }
                    else _login_resp.value = (ApiResult.error(it.body()!!.code))
                } else {
                    _login_resp.value = (ApiResult.networkError(it.code(), it.toString()))
                }
            }
        }
    }

    fun getUserProfile(pw : String){
        viewModelScope.launch {
            repository.getProfile().let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        val user = it.body()!!.result
                        user!!.password = pw
                        isProfileSuccess.value = true
                        repository.saveUser(user)
                    }
                    else isProfileSuccess.value = false
                }
                else isProfileSuccess.value = false
            }
        }
    }

    fun saveIsAutoLogin(status : Boolean){
        repository.saveIsAutoLogin(status)
    }

    fun saveTokens(tokens : LoginToken){
        repository.saveXcesToken(tokens.accessToken)
        repository.saveRefToken(tokens.refreshToken)
    }

    fun socialLogin(socialUserInfo : SocialLoginRequest){
        viewModelScope.launch {
            _socialLogin_resp.value = ApiResult.loading()
            repository.socialLogin(socialUserInfo).let {
                Log.d("hellohello~", it.toString())
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _socialLogin_resp.value = ApiResult.success(it.body()!!.result)
                    } else _socialLogin_resp.value = ApiResult.error(it.body()!!.code)
                } else _socialLogin_resp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun socialSignin(){
        viewModelScope.launch {
            _socialSignin_resp.value = ApiResult.loading()
            repository.socialSignIn(socialSigninRequest.value!!).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _socialSignin_resp.value = ApiResult.success(it.body()!!.result)
                    } else _socialSignin_resp.value = ApiResult.error(it.body()!!.code)
                } else _socialSignin_resp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }
//    fun autoLogin(){
//        viewModelScope.launch {
//            _login_resp.value = ApiResult.loading()
//            val user = getUser()
//            val request = LoginRequest(user!!.email, user.password!!)
//            repository.autoLogin(request).let {
//                if(it.isSuccessful){
//                    if(it.body()!!.code == 1000){
//                        // 엑세스 토큰, 리프레쉬 토큰 둘다 저장 (자동로그인에서만)
//                        repository.saveXcesToken(it.body()!!.result!!.token.accessToken)
//                        repository.saveRefToken(it.body()!!.result!!.token.refreshToken)
//                        _login_resp.value = ApiResult.success(it.body()!!.result)
//                    }
//                    else _login_resp.value = (ApiResult.error(it.body()!!.code))
//                } else {
//                    _login_resp.value = (ApiResult.networkError(it.code(), it.toString()))
//                }
//            }
//        }
//    }
}