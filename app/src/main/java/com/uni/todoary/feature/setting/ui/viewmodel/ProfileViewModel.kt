package com.uni.todoary.feature.setting.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.setting.data.module.ChangeProfileImgResponse
import com.uni.todoary.feature.setting.data.module.ProfileChangeRequest
import com.uni.todoary.feature.setting.data.repository.UserRepository
import com.uni.todoary.util.getUser
import com.uni.todoary.util.getXcesToken
import com.uni.todoary.util.saveUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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

    private val _changeImgResult = MutableLiveData<ApiResult<ChangeProfileImgResponse>>()
    val changeImgResult : LiveData<ApiResult<ChangeProfileImgResponse>>
        get() =_changeImgResult

    var _profileImgUrl : MutableLiveData<MultipartBody.Part?> = MutableLiveData()
    val profileImgUrl : LiveData<MultipartBody.Part?>
        get() =_profileImgUrl

    init {
        _user.value = repository.getUser()
        _profileImgUrl.value = null
        Log.d("prtprt1", profileImgUrl.value.toString())
    }

    fun getUser(){
        _user.value = repository.getUser()
    }

    fun setUri(uri : MultipartBody.Part){
        _profileImgUrl.value = uri
        Log.d("prtprt2", profileImgUrl.value.toString())
    }

    fun updateUser(name : String, intro : String){
        _updateResult.value = ApiResult.loading()
        viewModelScope.launch {
            repository.changeProfile(ProfileChangeRequest(name, intro)).let {
                Log.d("iit", it.toString())
                if(it.isSuccessful){
                    Log.d("iiiit", it.body().toString())
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

    fun changeProfileImg(){
        Log.d("prtprt", profileImgUrl.value.toString())
        if (this.profileImgUrl.value == null) {
            _changeImgResult.value = ApiResult.success(null)
            return
        }
        else {
            val request = this.profileImgUrl.value!!
            _changeImgResult.value = ApiResult.loading()
            viewModelScope.launch {
                repository.changeProfileImg(request).let {
                    if(it.isSuccessful){
                        if(it.body()!!.code == 1000){
                            _changeImgResult.value = ApiResult.success(it.body()!!.result)
                            val user = repository.getUser()!!
                            user.profileImgUrl = it.body()!!.result!!.profile_img_url
                            repository.saveUser(user)
                        } else _changeImgResult.value = ApiResult.error(it.body()!!.code)
                    } else _changeImgResult.value = ApiResult.networkError(it.code(), it.message())
                }
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