package com.example.learning1.di

import com.example.learning1.data.dto.UserDTO
import com.example.learning1.data.dto.UserRequestDTO
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("auth/login")
    suspend fun login(@Body userRequestDTO: UserRequestDTO) : Response<UserDTO>
}