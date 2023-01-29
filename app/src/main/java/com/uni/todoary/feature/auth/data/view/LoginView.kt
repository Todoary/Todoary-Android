package com.uni.todoary.feature.auth.data.view

import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginResponse

interface LoginView {
    fun loginLoading()
    fun loginSuccess(result : LoginResponse)
    fun loginFailure(code : Int)
}

interface FcmView{
    fun fcmLoading()
    fun fcmSuccess()
    fun fcmFailure(code : Int, message : String)
}

interface GetProfileView{
    fun getProfileLoading()
    fun getProfileSuccess(result : User)
    fun getProfileFailure(code : Int)
}