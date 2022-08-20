package com.uni.todoary.feature.auth.data.view

interface SignInView {
    fun SignInLoading()
    fun SignInSuccess()
    fun SignInFailure(code: Int)
}