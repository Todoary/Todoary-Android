package com.uni.todoary.feature.auth.data.repository

import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseAPIAction
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepoImpl : AuthRepository {
    private val authService= ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    override suspend fun login(
        request: LoginRequest,
        callback: BaseAPIAction<LoginResponse>
    ) {
        callback.onLoading()
        authService.login(request).enqueue(object : Callback<BaseResponse<LoginResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<LoginResponse>>,
                response: Response<BaseResponse<LoginResponse>>
            ) {
                val resp = response.body()!!
                when (resp.code){
                    1000 -> callback.onSuccess(resp.result!!)
                    else -> callback.onFail(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

}