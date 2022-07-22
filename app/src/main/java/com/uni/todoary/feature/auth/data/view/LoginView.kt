package com.uni.todoary.feature.auth.data.view

import com.uni.todoary.feature.auth.data.dto.LoginResponse

interface LoginView {
    fun loginLoading()
    fun loginSuccess(result : LoginResponse)
    fun loginFailure(code : Int)
}