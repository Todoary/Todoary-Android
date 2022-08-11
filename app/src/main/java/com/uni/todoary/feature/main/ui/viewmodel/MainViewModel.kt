package com.uni.todoary.feature.main.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.main.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : MainRepository) : ViewModel(){
    val _user : MutableLiveData<User> = MutableLiveData<User>()
    val user : LiveData<User>  get() = _user

    init {
        _user.value = repository.getUser()
    }
}