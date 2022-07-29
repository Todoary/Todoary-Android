package com.uni.todoary.feature.auth.data.view

import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.dto.User

interface LoginView {
    fun loginLoading()
    fun loginSuccess(result : LoginResponse)
    fun loginFailure(code : Int)
}

interface GetProfileView{
    fun getProfileLoading()
    fun getProfileSuccess(result : User)
    fun getProfileFailure(code : Int)
}