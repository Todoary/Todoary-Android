package com.uni.todoary.feature.setting.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.setting.data.module.ProfileChangeRequest
import com.uni.todoary.feature.setting.data.repository.UserRepository
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository : UserRepository
): ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    private val _updateResult = MutableLiveData<ApiResult<Any>>()
    val updateResult : LiveData<ApiResult<Any>>
        get() =_updateResult

    private val _deleteResult = MutableLiveData<ApiResult<Any>>()
    val deleteResult : LiveData<ApiResult<Any>>
        get() =_deleteResult

    private val _logOutResult = MutableLiveData<ApiResult<Any>>()
    val logOutResult : LiveData<ApiResult<Any>>
        get() =_logOutResult

    init {
        _user.value = repository.getUser()
    }

    fun getUser(){
        _user.value = repository.getUser()
    }

    fun updateUser(name : String, intro : String){
        _updateResult.value = ApiResult.loading()
        viewModelScope.launch {
            repository.changeProfile(ProfileChangeRequest(name, intro)).let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        val user = repository.getUser()!!
                        user.nickname = it.body()!!.result!!.nickname
                        user.introduce = it.body()!!.result!!.introduce
                        _user.value = user
                        repository.saveUser(user)
                        _updateResult.value = ApiResult.success(null)
                    } else {
                        _updateResult.value = ApiResult.error(it.body()!!.code)
                    }
                } else {
                    _updateResult.value = ApiResult.networkError(it.code(), it.toString())
                }
            }
        }
    }

    fun deleteUser(){
        _deleteResult.value = ApiResult.loading()
        viewModelScope.launch {
            repository.deleteUser().let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        // 캐싱 유저정보 삭제
                        repository.removeUser()
                        repository.removeXcesToken()
                        repository.removeRefToken()
                        repository.saveIsAutoLogin(false)
                        _deleteResult.value = ApiResult.success(null)
                    } else _deleteResult.value = ApiResult.error(it.body()!!.code)
                } else _deleteResult.value = ApiResult.networkError(it.code(), it.toString())
            }
        }
    }

    fun logOut(){
        _logOutResult.value = ApiResult.loading()
        viewModelScope.launch {
            repository.logOut().let {
                if(it.isSuccessful){
                    if(it.body()!!.code == 1000){
                        // 캐싱 유저정보 삭제
                        repository.removeUser()
                        repository.removeXcesToken()
                        repository.removeRefToken()
                        repository.saveIsAutoLogin(false)
                        _logOutResult.value = ApiResult.success(null)
                    } else _logOutResult.value = ApiResult.error(it.body()!!.code)
                } else _logOutResult.value = ApiResult.networkError(it.code(), it.toString())
            }
        }
    }
}