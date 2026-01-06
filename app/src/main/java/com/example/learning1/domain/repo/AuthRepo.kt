package com.example.learning1.domain.repo

import com.example.learning1.data.dto.NetworkResponse
import com.example.learning1.domain.entity.User

interface AuthRepo {
    suspend fun login(email:String,password:String) : NetworkResponse<User>
}