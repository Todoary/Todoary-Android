package com.uni.todoary.util

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.AgreeTermsRequest
import com.uni.todoary.feature.auth.data.dto.ProfileChangeRequest
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.category.data.dto.CategoryChangeRequest
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.dto.CheckBoxRequest
import com.uni.todoary.feature.main.data.dto.GetDiaryRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.*

interface RetrofitInterface {
    // --------- Auth ------------- //
    @POST("/auth/signin")
    fun login(@Body loginInfo: LoginRequest): Call<BaseResponse<LoginResponse>>

    @POST("/auth/signin/auto")
    fun autoLogin(@Body loginInfo: LoginRequest) : Call<BaseResponse<LoginResponse>>

    @POST("/auth/signup")
    fun SignIn(@Body signinInfo : SignInRequest) : Call<BaseResponse<Any>>

    @GET("/auth/email/duplication")
    fun EmailCheck(@Query("email") email : String) : Call<BaseResponse<Any>>

    @GET("/auth/email/existence")
    fun ExistenceCheck(@Query("email") email : String) : Call<BaseResponse<Any>>




    // ------------ Profile ------------- //
    @GET("/users")
    fun GetProfile() : Call<BaseResponse<User>>

    @PATCH("/users/profile")
    fun ProfileChange(@Body ChangeProfileInfo : ProfileChangeRequest) : Call<BaseResponse<Any>>

    @PATCH("/users/status")
    fun DeleteMember() : Call<BaseResponse<User>>


    @PATCH("/users/service/terms")
    fun AgreeTerms(@Body AgreeTermsInfo: Boolean) : Call<BaseResponse<Any>>

    // ------------ To_Do ------------- //
    @PATCH("/todo/check")
    fun CheckBox(@Body CheckBoxInfo : CheckBoxRequest) : Call<BaseResponse<Any>>

    // ------------ Category ------------- //
    @POST("/category")
    fun CategoryAdd(@Body CateogoryAddInfo : CategoryChangeRequest) : Call<BaseResponse<Any>>

    @GET("/category")
    fun GetCategory() : Call<BaseResponse<List<CategoryData>>>

    @PATCH("/category/{categoryId}")
    fun CategoryChange(
        @Path("categoryId") categoryId : Long,
        @Body CategoryChangeInfo : CategoryChangeRequest
    ) : Call<BaseResponse<Any>>

    @DELETE("/category/{categoryId}")
    fun CategoryDelete(@Path("categoryId") categoryId: Long) : Call<BaseResponse<Any>>

    // ------------ Diary ------------- //
    @POST("/diary/{createdDate}")
    fun AddDiary(
        @Path("createdDate") createdDate : String,
        @Body addDiaryInfo : AddDiaryRequest
    ) : Call<BaseResponse<Any>>

    @GET("/diary")
    fun GetDiary(@Query("createdDate") createdDate : String) : Call<BaseResponse<GetDiaryRequest>>

    @DELETE("/diary/{createdDate}")
    fun DeleteDiary(
        @Path("createdDate") createdDate : String
    ) : Call<BaseResponse<Any>>
}