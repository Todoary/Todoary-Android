package com.uni.todoary.feature.auth.data.view

interface EmailCheckView {
    fun EmailCheckLoading()
    fun EmailCheckSuccess()
    fun EmailCheckFailure(code: Int)
}