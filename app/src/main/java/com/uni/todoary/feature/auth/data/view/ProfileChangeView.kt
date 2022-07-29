package com.uni.todoary.feature.auth.data.view

interface ProfileChangeView {
    fun ProfileChangeLoading()
    fun ProfileChangeSuccess()
    fun ProfileChangeFailure(code: Int)
}