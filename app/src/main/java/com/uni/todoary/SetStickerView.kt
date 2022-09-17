package com.uni.todoary

interface SetStickerView {
    fun SetStickerLoading()
    fun SetStickerSuccess()
    fun SetStickerFailure(code: Int)
}