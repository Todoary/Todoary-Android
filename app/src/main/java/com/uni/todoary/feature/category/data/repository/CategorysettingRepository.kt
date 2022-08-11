package com.uni.todoary.feature.category.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.module.CreateTodoRequest
import com.uni.todoary.feature.category.data.module.CreateTodoResponse
import com.uni.todoary.feature.category.data.module.TodoInterface
import retrofit2.Response
import javax.inject.Inject

class TodoRepository @Inject constructor(private val remoteApi : TodoInterface){
    suspend fun createTodo(request : CreateTodoRequest) : Response<BaseResponse<CreateTodoResponse>> {
        return remoteApi.createTodo(request)
    }

    suspend fun editTodo(request : CreateTodoResponse, todoId : Int) : Response<BaseResponse<Any>> {
        return remoteApi.editTodo(request, todoId)
    }

    suspend fun deleteTodo(todoId : Int) : Response<BaseResponse<Any>> {
        return remoteApi.deleteTodo(todoId)
    }

    suspend fun getCategoryList() : Response<BaseResponse<ArrayList<CategoryData>>> {
        return remoteApi.getCategoryList()
    }
}