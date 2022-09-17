package com.uni.todoary.feature.main.data.view

interface DeleteDiaryView {
    fun DeleteDiaryLoading()
    fun DeleteDiarySuccess()
    fun DeleteDiaryFailure(code: Int)
}