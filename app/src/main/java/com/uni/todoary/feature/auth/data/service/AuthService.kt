package com.uni.todoary.feature.auth.data.service

import android.util.Log
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private val authService= ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView){
        this.loginView=loginView
    }

    fun logIn(request : LoginRequest){
        loginView.loginLoading()
        authService.login(request).enqueue(object: Callback<BaseResponse<LoginResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<LoginResponse>>,
                response: Response<BaseResponse<LoginResponse>>
            ) {
                val resp = response.body()!!
                when (resp.code){
                    1000 -> loginView.loginSuccess(resp.result!!)
                    else -> loginView.loginFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                Log.d("login_API_Failure", t.toString())
            }
        })
    }
}