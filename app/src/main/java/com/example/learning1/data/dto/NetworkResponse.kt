package com.example.learning1.data.dto

sealed class NetworkResponse<out T>{
    data class Success<T>(val response : T) : NetworkResponse<T>()
    data class NetworkError(val statusCode : Int, val message : String) : NetworkResponse<Nothing>()
    data class Exception(val message : String) : NetworkResponse<Nothing>()
}