package com.uni.todoary.feature.auth.data.view

interface AgreeTermsView {
    fun AgreeTermsLoading()
    fun AgreeTermsSuccess()
    fun AgreeTermsFailure(code: Int)
}