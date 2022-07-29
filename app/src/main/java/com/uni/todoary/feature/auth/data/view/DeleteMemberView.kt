package com.uni.todoary.feature.auth.data.view

interface DeleteMemberView {
    fun DeleteMemberLoading()
    fun DeleteMemberSuccess()
    fun DeleteMemberFailure(code: Int)
}