package com.uni.todoary

import android.util.Log
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.auth.data.view.GetProfileView
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.feature.auth.data.dto.*
import com.uni.todoary.feature.auth.data.view.*
import com.uni.todoary.feature.category.data.dto.CategoryChangeRequest
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.category.data.view.CategoryChangeView
import com.uni.todoary.feature.category.data.view.CategoryDeleteView
import com.uni.todoary.feature.category.data.view.GetCategoryView
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.dto.CheckBoxRequest
import com.uni.todoary.feature.main.data.dto.GetSticker
import com.uni.todoary.feature.main.data.dto.SetSticker
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.feature.main.data.view.CheckBoxView
import com.uni.todoary.feature.main.data.view.GetDiaryView
import com.uni.todoary.util.RetrofitInterface
import com.uni.todoary.util.getXcesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class StickerService {
    private val stickerService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    private lateinit var SetStickerView: SetStickerView
    private lateinit var GetStickerView: GetStickerView

    fun setSetStickerView(SetStickerView: SetStickerView) {
        this.SetStickerView = SetStickerView
    }

    fun setGetStickerView(GetStickerView: GetStickerView) {
        this.GetStickerView = GetStickerView
    }

    fun SetSticker(date : String, setSticker : SetSticker){
        SetStickerView.SetStickerLoading()
        stickerService.SetSticker(date, setSticker).enqueue(object : Callback<BaseResponse<List<Long>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Long>>>,
                response: Response<BaseResponse<List<Long>>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> SetStickerView.SetStickerSuccess()
                    else -> SetStickerView.SetStickerFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Long>>>, t: Throwable) {
                Log.d("SetSticker_API_Failure", t.toString())
            }
        })
    }

    fun GetSticker(date : String){
        GetStickerView.GetStickerLoading()
        stickerService.GetSticker(date).enqueue(object : Callback<BaseResponse<List<GetSticker>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<GetSticker>>>,
                response: Response<BaseResponse<List<GetSticker>>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> GetStickerView.GetStickerSuccess(resp.result)
                    else -> GetStickerView.GetStickerFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<GetSticker>>>, t: Throwable) {
                Log.d("GetSticker_API_Failure", t.toString())
            }
        })
    }
}

