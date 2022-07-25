package com.uni.todoary.feature.auth.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.BaseAPIAction
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.repository.AuthRepoImpl
import com.uni.todoary.feature.auth.data.repository.AuthRepository
import com.uni.todoary.feature.auth.data.view.LoginView
import kotlinx.coroutines.launch

class LoginViewModel constructor (private val repository : AuthRepository) : ViewModel() {

    val loginRequest : MutableLiveData<LoginResponse> by lazy{
        MutableLiveData<LoginResponse>()
    }

    fun login(view : LoginView, request : LoginRequest){
        viewModelScope.launch {
            repository.login(request, object : BaseAPIAction<LoginResponse>{
                override fun onSuccess(data: LoginResponse) {
                    loginRequest.value = data
                }
                override fun onFail(code: Int) {
                    view.loginFailure(code)
                }
                override fun onError(throwable: Throwable) {
                    Log.d("login_API_Error", throwable.toString())
                }
                override fun onLoading() {
                    view.loginLoading()
                }
            })
        }
    }
}