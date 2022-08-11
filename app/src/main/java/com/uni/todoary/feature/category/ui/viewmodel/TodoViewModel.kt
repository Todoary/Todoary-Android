package com.uni.todoary.feature.category.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.repository.TodoRepository
import com.uni.todoary.feature.main.data.dto.TodoListAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository : TodoRepository) : ViewModel() {
    private val _date : MutableLiveData<LocalDate> = MutableLiveData()
    val date : LiveData<LocalDate> get() = _date
    private val _categoryList : MutableLiveData<ApiResult<ArrayList<CategoryData>>> = MutableLiveData()
    val categoryList : LiveData<ApiResult<ArrayList<CategoryData>>> get() = _categoryList

    private var todoContent : String  = ""
    private var alarmEnable : Boolean = false
    private var alarmInfo : String = ""
    private var categoryIdx = 0L

    init {
        _date.value = LocalDate.now()
        initCategoryList()
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
}