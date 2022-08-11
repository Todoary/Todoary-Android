package com.uni.todoary.feature.category.data.view

interface CategoryChangeView {
    fun CategoryChangeLoading()
    fun CategoryChangeSuccess()
    fun CategoryChangeFailure(code: Int)
}