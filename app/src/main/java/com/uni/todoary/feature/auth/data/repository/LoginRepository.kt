package com.uni.todoary.feature.auth.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.module.LoginInterface
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.module.LoginResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val remoteApi : LoginInterface) {
    suspend fun login(request : LoginRequest) : Response<BaseResponse<LoginResponse>>{
        return remoteApi.login(request)
    }
}