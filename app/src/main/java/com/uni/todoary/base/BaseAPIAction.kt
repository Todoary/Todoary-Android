package com.uni.todoary.base

interface BaseAPIAction<T> {
    fun onSuccess(data: T)

    fun onFail(code : Int)

    fun onError(throwable: Throwable)

    fun onLoading()
}