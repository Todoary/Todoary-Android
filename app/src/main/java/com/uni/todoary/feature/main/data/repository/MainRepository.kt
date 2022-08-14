package com.uni.todoary.feature.main.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.AuthSpfManager
import com.uni.todoary.feature.main.data.module.MainApiInterface
import com.uni.todoary.feature.main.data.module.TodoListResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteApi : MainApiInterface, private val spf : AuthSpfManager){

    suspend fun getTodoList(date : String) : Response<BaseResponse<ArrayList<TodoListResponse>>> {
        return remoteApi.getTodoList(date)
    }

    // --------------- SPF Manager ---------------- //
    fun getUser() : User = spf.getUser()!!
}