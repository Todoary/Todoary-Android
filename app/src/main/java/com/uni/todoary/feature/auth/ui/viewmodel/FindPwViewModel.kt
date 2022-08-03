package com.uni.todoary.feature.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.uni.todoary.feature.auth.data.repository.FindPwRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(private val repository : FindPwRepository): ViewModel() {
}