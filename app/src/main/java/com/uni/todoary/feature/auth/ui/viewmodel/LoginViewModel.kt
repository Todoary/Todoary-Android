package com.uni.todoary.feature.auth.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.auth.data.repository.LoginRepository
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.util.saveRefToken
import com.uni.todoary.util.saveXcesToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val _login_resp = MutableLiveData<ApiResult<LoginResponse>>()
    val login_resp : MutableLiveData<ApiResult<LoginResponse>>
        get() =_login_resp

    fun login(request : LoginRequest) {
        viewModelScope.launch {
            _login_resp.value = ApiResult.loading()
            repository.login(request).let{
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){

                        _login_resp.value = ApiResult.success(it.body()!!.result)
                    }
                    else _login_resp.value = (ApiResult.error(it.body()!!.code))
                    Log.d("Login_AAA", "qrqrqrqrqrqrqr")

                } else {
                    Log.d("dkfk", it.toString())
                    _login_resp.value = (ApiResult.networkError(it.code(), it.toString()))
                }
            }
        }
    }
}