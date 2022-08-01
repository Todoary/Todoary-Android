package com.uni.todoary.feature.auth.data.repository

import com.uni.todoary.feature.auth.data.module.LoginInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindPwRepository @Inject constructor(private val remoteApi : LoginInterface){
}