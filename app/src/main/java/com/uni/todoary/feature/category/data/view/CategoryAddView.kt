package com.uni.todoary.feature.category.data.view

interface CategoryAddView {
    fun CategoryAddLoading()
    fun CategoryAddSuccess()
    fun CategoryAddFailure(code: Int)
}