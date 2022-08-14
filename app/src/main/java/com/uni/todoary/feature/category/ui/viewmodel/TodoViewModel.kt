package com.uni.todoary.feature.category.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.module.CreateTodoRequest
import com.uni.todoary.feature.category.data.module.CreateTodoResponse
import com.uni.todoary.feature.category.data.repository.TodoRepository
import com.uni.todoary.feature.main.data.dto.TodoListAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository : TodoRepository) : ViewModel() {
    private val _date : MutableLiveData<LocalDate> = MutableLiveData()
    val date : LiveData<LocalDate> get() = _date

    private val _categoryList : MutableLiveData<ApiResult<ArrayList<CategoryData>>> = MutableLiveData()
    val categoryList : LiveData<ApiResult<ArrayList<CategoryData>>> get() = _categoryList

    private val _createTodoResp : MutableLiveData<ApiResult<CreateTodoResponse>> = MutableLiveData()
    val createTodoResp : LiveData<ApiResult<CreateTodoResponse>> get() = _createTodoResp

    private val _editTodoResp : MutableLiveData<ApiResult<Any>> = MutableLiveData()
    val editTodoResp : LiveData<ApiResult<Any>> get() = _editTodoResp

    private val _deleteTodoResp : MutableLiveData<ApiResult<Any>> = MutableLiveData()
    val deleteTodoResp : LiveData<ApiResult<Any>> get() = _deleteTodoResp

    private var todoContent : String  = ""
    private var alarmEnable : Boolean = false
    private var alarmInfo : String = ""
    private var categoryIdx = 0L

    init {
        _date.value = LocalDate.now()
    }

    fun setContent(content : String){
        this.todoContent = content
    }

    fun setCategoryIdx(idx : Long){
        this.categoryIdx = idx
    }

    fun setAlarmInfo(enable : Boolean, hour : Int?, minute : Int?){
        if(enable){
            this.alarmInfo = "${hour}:${minute}"
        } else {
            this.alarmInfo = ""
        }
        this.alarmEnable = enable
    }

    fun setDate(date : LocalDate){
        _date.value = date
    }

    fun initCategoryList(){
        viewModelScope.launch {
            _categoryList.value = ApiResult.loading()
            repository.getCategoryList().let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _categoryList.value = ApiResult.success(it.body()!!.result)
                    } else _categoryList.value = ApiResult.error(it.body()!!.code)
                } else _categoryList.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

   fun createTodo(){
       val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
       val dateString = date.value!!.format(formatter)
       val request = CreateTodoRequest(todoContent, dateString ,alarmEnable, alarmInfo, categoryIdx)
        viewModelScope.launch{
            _createTodoResp.value = ApiResult.loading()
            repository.createTodo(request).let{
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _createTodoResp.value = ApiResult.success(it.body()!!.result)
                    } else _createTodoResp.value = ApiResult.error(it.body()!!.code)
                } else _createTodoResp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun editTodo(request : CreateTodoRequest, todoId : Int){
        viewModelScope.launch {
            _editTodoResp.value = ApiResult.loading()
            repository.editTodo(request, todoId).let{
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _editTodoResp.value = ApiResult.success(it.body()!!.result)
                    } else _editTodoResp.value = ApiResult.error(it.body()!!.code)
                } else _editTodoResp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun deleteTodo(todoId : Int){
        viewModelScope.launch {
            _deleteTodoResp.value = ApiResult.loading()
            repository.deleteTodo(todoId).let{
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _deleteTodoResp.value = ApiResult.success(it.body()!!.result)
                    } else _deleteTodoResp.value = ApiResult.error(it.body()!!.code)
                } else _deleteTodoResp.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }
}