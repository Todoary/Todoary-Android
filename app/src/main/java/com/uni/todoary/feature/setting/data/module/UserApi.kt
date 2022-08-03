package com.uni.todoary.feature.setting.data.module

import com.google.gson.annotations.SerializedName
import com.uni.todoary.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

data class ProfileChangeRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("introduce") val introduce : String
)

interface UserInterface {
    @PATCH("/users/profile")
    suspend fun changeProfile(@Body ChangeProfileInfo : ProfileChangeRequest) : Response<BaseResponse<ProfileChangeRequest>>

    @PATCH("/users/status")
    suspend fun deleteUser() : Response<BaseResponse<Any>>

    @POST("/users/signout")
    suspend fun logOut() : Response<BaseResponse<Any>>
}