package com.uni.todoary.feature.main.data.view

interface CheckBoxView {
    fun CheckBoxLoading()
    fun CheckBoxSuccess()
    fun CheckBoxFailure(code: Int)
}