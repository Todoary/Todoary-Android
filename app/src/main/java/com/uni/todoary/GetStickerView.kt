package com.uni.todoary

import com.uni.todoary.feature.main.data.dto.GetSticker

interface GetStickerView {
    fun GetStickerLoading()
    fun GetStickerSuccess(result : List<GetSticker>?)
    fun GetStickerFailure(code: Int)
}