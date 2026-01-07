package com.example.learning1.data.repo

import com.example.learning1.data.dto.NetworkResponse
import com.example.learning1.data.dto.UserRequestDTO
import com.example.learning1.data.mappers.toUser
import com.example.learning1.di.APIService
import com.example.learning1.domain.entity.User
import com.example.learning1.domain.repo.AuthRepo
import org.json.JSONObject
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(val apiService: APIService) : AuthRepo {
    override suspend fun login(email: String, password: String): NetworkResponse<User> {

        try {
            val result = apiService.login(UserRequestDTO(email, password))
            if (result.isSuccessful && result.body() != null) {
                return NetworkResponse.Success(result.body()!!.toUser())
            } else {

                val msg = result.errorBody()?.string()

                val message = runCatching { JSONObject(msg?:"").getString("message") }.getOrElse {
                    msg ?: "Unknown error"
                }

                return NetworkResponse.NetworkError(result.code(), message)
            }
        } catch (e: Exception) {
            return NetworkResponse.Exception(e.toString())
        }
    }
}