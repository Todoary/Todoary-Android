package com.uni.todoary.feature.main.data.view

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.databinding.FragmentCalendarBinding
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.dto.GetDiaryRequest

interface GetDiaryView {
    fun GetDiaryLoading()
    fun GetDiarySuccess(code:Int,result: GetDiaryRequest)
    fun GetDiaryFailure(code: Int)
}