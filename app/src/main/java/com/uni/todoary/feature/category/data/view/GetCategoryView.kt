package com.uni.todoary.feature.category.data.view

import com.uni.todoary.feature.category.data.dto.CategoryData

interface GetCategoryView {
    fun GetCategoryLoading()
    fun GetCategorySuccess(result: List<CategoryData>)
    fun GetCategoryFailure(code: Int)
}