package com.uni.todoary.feature.auth.data.repository

import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseAPIAction
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.util.RetrofitInterface

interface AuthRepository {
    suspend fun login(request : LoginRequest, callback : BaseAPIAction<LoginResponse>)
}