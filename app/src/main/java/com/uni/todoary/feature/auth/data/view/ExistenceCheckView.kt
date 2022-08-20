package com.uni.todoary.feature.auth.data.view

interface ExistenceCheckView {
    fun ExistenceCheckLoading()
    fun ExistenceCheckSuccess()
    fun ExistenceCheckFailure(code: Int)
}