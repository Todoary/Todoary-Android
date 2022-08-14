package com.uni.todoary.feature.main.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.main.data.module.TodoListResponse
import com.uni.todoary.feature.main.data.repository.MainRepository
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

    init {
        _user.value = repository.getUser()
        date.value = LocalDate.now()
        getTodoList()
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
                    } else _todoListResposne.value = ApiResult.error(it.code())
                } else _todoListResposne.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }
}