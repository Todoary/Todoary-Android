package com.uni.todoary.feature.category.data.module

import com.google.gson.annotations.SerializedName
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.main.data.module.TodoCheckRequest
import retrofit2.Response
import retrofit2.http.*

data class CreateTodoRequest(
    @SerializedName("title") val title : String,
    @SerializedName("targetDate") val targetDate : String,
    @SerializedName("isAlarmEnabled") val isAlarmEnabled : Boolean,
    @SerializedName("targetTime") val targetTime : String,
    @SerializedName("categoryId") val categoryId : Long,
)

data class CreateTodoResponse(
    @SerializedName("todoId") val todoId : Int
    )

data class TodoInfo(
    @SerializedName("todoId") val todoId : Long,
    @SerializedName("isChecked") val isChecked : Boolean,
    @SerializedName("title") val title : String,
    @SerializedName("targetDate") val targetDate : String,
    @SerializedName("isAlarmEnabled") val isAlarmEnabled : Boolean,
    @SerializedName("targetTime") val targetTime : String,
    @SerializedName("createdTime") val createdTime : String,
    @SerializedName("categoryId") val categoryId : Long,
    @SerializedName("categoryTitle") val categoryTitle : String,
    @SerializedName("color") val color : Int
    )


interface TodoInterface {
    @POST("/todo")
    suspend fun createTodo(
        @Body request : CreateTodoRequest
    ) : Response<BaseResponse<CreateTodoResponse>>

    @PATCH("/todo/{todoid}")
    suspend fun editTodo(
        @Body request : CreateTodoRequest,
        @Path("todoid") todoId : Int
    ) : Response<BaseResponse<Any>>

    @DELETE("/todo/{todoId}")
    suspend fun deleteTodo(
        @Path("todoid") todoId : Int
    ) : Response<BaseResponse<Any>>

    @GET("/category")
    suspend fun getCategoryList() : Response<BaseResponse<ArrayList<CategoryData>>>

    @GET("/todo/category/{categoryId}")
    suspend fun getCategoryTodo(
        @Path("categoryId") categoryId : Long
    ) : Response<BaseResponse<ArrayList<TodoInfo>>>

    @PATCH("/todo/check")
    suspend fun todoCheck(
        @Body request : TodoCheckRequest
    ) : Response<BaseResponse<Any>>
}