package com.uni.todoary.feature.main.data.view

interface GetDiaryView {
    fun GetDiaryLoading()
    fun GetDiarySuccess()
    fun GetDiaryFailure(code: Int)
}