package com.uni.todoary.feature.setting.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.setting.data.module.AlarmStatus
import com.uni.todoary.feature.setting.data.repository.UserRepository
import com.uni.todoary.feature.setting.ui.view.AlarmActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val repository : UserRepository) : ViewModel(){

    private val _alarmApiResult = MutableLiveData<ApiResult<AlarmStatus>>()
    val alarmApiResult : LiveData<ApiResult<AlarmStatus>>
        get() = _alarmApiResult

    private val _updateAlarmApiResult = MutableLiveData<ApiResult<AlarmActivity.AlarmType>>()
    val updateAlarmApiResult : LiveData<ApiResult<AlarmActivity.AlarmType>>
        get() = _updateAlarmApiResult

    init {
        _alarmApiResult.value = ApiResult.loading()
        viewModelScope.launch {
            repository.getAlarmStatus().let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        _alarmApiResult.value = ApiResult.success(it.body()!!.result)
                    } else _alarmApiResult.value = ApiResult.error(it.body()!!.code)
                } else _alarmApiResult.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }

    fun updateAlarmStatus(type : AlarmActivity.AlarmType, status : Boolean){
        _updateAlarmApiResult.value = ApiResult.loading()
        viewModelScope.launch {
            when (type){
                AlarmActivity.AlarmType.TODO -> repository.updateAlarmTodo(status).let {
                    if(it.isSuccessful){
                        if(it.body()!!.code == 1000){
                            _updateAlarmApiResult.value = ApiResult.success(AlarmActivity.AlarmType.TODO)
                        } else _updateAlarmApiResult.value = ApiResult.error(it.body()!!.code)
                    } else _updateAlarmApiResult.value = ApiResult.networkError(it.code(), it.message())
                }
                AlarmActivity.AlarmType.DIARY -> repository.updateAlarmDiary(status).let {
                    if(it.isSuccessful){
                        if(it.body()!!.code == 1000){
                            _updateAlarmApiResult.value = ApiResult.success(AlarmActivity.AlarmType.DIARY)
                        } else _updateAlarmApiResult.value = ApiResult.error(it.body()!!.code)
                    } else _updateAlarmApiResult.value = ApiResult.networkError(it.code(), it.message())
                }
                AlarmActivity.AlarmType.REMIND -> repository.updateAlarmRemind(status).let{
                    if(it.isSuccessful){
                        if(it.body()!!.code == 1000){
                            _updateAlarmApiResult.value = ApiResult.success(AlarmActivity.AlarmType.REMIND)
                        } else _updateAlarmApiResult.value = ApiResult.error(it.body()!!.code)
                    } else _updateAlarmApiResult.value = ApiResult.networkError(it.code(), it.message())
                }
            }
        }
    }
}