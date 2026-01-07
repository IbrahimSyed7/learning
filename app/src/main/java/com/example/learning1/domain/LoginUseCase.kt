package com.example.learning1.domain

import com.example.learning1.data.dto.NetworkResponse
import com.example.learning1.domain.entity.User
import com.example.learning1.domain.repo.AuthRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repo: AuthRepo) {
    suspend operator fun invoke(email:String,password:String) : NetworkResponse<User>{
       return repo.login(email,password)
    }
}