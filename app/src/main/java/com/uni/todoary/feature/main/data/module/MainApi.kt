package com.uni.todoary.feature.main.data.module

import com.google.gson.annotations.SerializedName
import com.uni.todoary.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

data class TodoListResponse(
    @SerializedName("todoId") val todoId : Long,
    @SerializedName("isPinned") val isPinned : Boolean,
    @SerializedName("isChecked") val isChecked : Boolean,
    @SerializedName("title") val title : String,
    @SerializedName("targetDate") val targetDate : String,
    @SerializedName("isAlarmEnabled") val isAlarmEnabled : Boolean,
    @SerializedName("targetTime") val targetTime : String,
    @SerializedName("createdTime") val createdTime : String,
    @SerializedName("categoryId") val categoryId : Long,
    @SerializedName("categoryTitle") val categoryTitle : String,
    @SerializedName("color") val color : Int,
)

data class TodoCheckRequest(
    @SerializedName("todoId") val todoId : Long,
    @SerializedName("isChecked") val isChecked : Boolean
    )

interface MainApiInterface {
    @GET("/todo/date/{date}")
    suspend fun getTodoList(
        @Path("date") date : String
    ) : Response<BaseResponse<ArrayList<TodoListResponse>>>

    @PATCH("/todo/check")
    suspend fun todoCheck(
        @Body request : TodoCheckRequest
    ) : Response<BaseResponse<Any>>

    @GET("/todo/days/{year-month}")
    suspend fun getCalendarInfo(
        @Path("year-month") yearMonth : String
    ) : Response<BaseResponse<ArrayList<Int>>>
}