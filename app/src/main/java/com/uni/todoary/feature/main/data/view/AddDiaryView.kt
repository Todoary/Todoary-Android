package com.uni.todoary.feature.main.data.view

interface AddDiaryView {
    fun AddDiaryLoading()
    fun AddDiarySuccess()
    fun AddDiaryFailure(code: Int)
}