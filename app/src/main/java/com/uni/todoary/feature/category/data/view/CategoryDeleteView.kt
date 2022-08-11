package com.uni.todoary.feature.category.data.view

interface CategoryDeleteView {
    fun CategoryDeleteLoading()
    fun CategoryDeleteSuccess()
    fun CategoryDeleteFailure(code: Int)
}