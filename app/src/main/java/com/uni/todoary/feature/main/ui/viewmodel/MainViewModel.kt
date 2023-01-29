package com.uni.todoary.feature.main.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.main.data.module.TodoCheckRequest
import com.uni.todoary.feature.main.data.module.TodoListResponse
import com.uni.todoary.feature.main.data.module.TodoPinRequest
import com.uni.todoary.feature.main.data.repository.MainRepository
import com.uni.todoary.util.yearMonthFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : MainRepository) : ViewModel(){
    val _user : MutableLiveData<User> = MutableLiveData<User>()
    val user : LiveData<User>  get() = _user
    val date : MutableLiveData<LocalDate> = MutableLiveData()

    var _todoListResposne : MutableLiveData<ApiResult<ArrayList<TodoListResponse>>> = MutableLiveData()
    val todoListResponse : LiveData<ApiResult<ArrayList<TodoListResponse>>>
        get() = _todoListResposne

    var _todoCheckResponse : MutableLiveData<ApiResult<Any>> = MutableLiveData()
    val todoCheckResponse : LiveData<ApiResult<Any>>
        get() = _todoCheckResponse

    var _todoDeleteResponse : MutableLiveData<ApiResult<Any>> = MutableLiveData()
    val todoDeleteResponse : LiveData<ApiResult<Any>>
        get() = _todoDeleteResponse

    var _getCalendarInfoResp : MutableLiveData<ApiResult<ArrayList<Int>>> = MutableLiveData()
    val getCalendarInfoResp : LiveData<ApiResult<ArrayList<Int>>>
        get() = _getCalendarInfoResp

    var _todoPinResp : MutableLiveData<ApiResult<Int>> = MutableLiveData()
    val todoPinResp : LiveData<ApiResult<Int>>
        get() = _todoPinResp

    init {
        _user.value = repository.getUser()
        date.value = LocalDate.now()
        getTodoList()
    }

    fun getUser(){
        _user.value = repository.getUser()
    }

    fun getTodoList(){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = date.value!!.format(formatter)
        viewModelScope.launch {
            _todoListResposne.value = ApiResult.loading()
            repository.getTodoList(dateString).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _todoListResposne.value = ApiResult.success(it.body()!!.result)
                    } else _todoListResposne.value = ApiResult.error(it.body()!!.code)
                } else _todoListResposne.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun todoCheck(todoId : Long , isChecked : Boolean){
        viewModelScope.launch {
            _todoCheckResponse.value = ApiResult.loading()
            val request = TodoCheckRequest(todoId, isChecked)
            repository.todoCheck(request).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _todoCheckResponse.value = ApiResult.success(it.body()!!.result)
                    } else _todoCheckResponse.value = ApiResult.error(it.body()!!.code)
                } else _todoCheckResponse.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun todoDelete(todoId : Long){
        viewModelScope.launch {
            _todoDeleteResponse.value = ApiResult.loading()
            repository.todoDelete(todoId).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _todoDeleteResponse.value = ApiResult.success(it.body()!!.result)
                    } else _todoDeleteResponse.value = ApiResult.error(it.body()!!.code)
                } else _todoDeleteResponse.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun getCalendarInfo(){
        val yearMonth = yearMonthFormatter(date.value!!)
        viewModelScope.launch {
            _getCalendarInfoResp.value = ApiResult.loading()
            repository.getCalendarInfo(yearMonth).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _getCalendarInfoResp.value = ApiResult.success(it.body()!!.result)
                    } else _getCalendarInfoResp.value = ApiResult.error(it.body()!!.code)
                } else _getCalendarInfoResp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun todoPin(todoId : Long, status : Boolean, position : Int){
        val request = TodoPinRequest(todoId, status)
        viewModelScope.launch {
            _todoPinResp.value = ApiResult.loading()
            repository.todoPin(request).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _todoPinResp.value = ApiResult.success(position)
                    } else _todoPinResp.value = ApiResult.error(it.body()!!.code)
                } else _todoPinResp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }
}