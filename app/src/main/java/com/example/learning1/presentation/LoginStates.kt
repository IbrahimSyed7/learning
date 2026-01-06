package com.example.learning1.presentation



data class LoginStates (
    val username : String = "",
    val password : String = "",
    val userNameError  : String? = null,
    val passwordError : String? = null,
    val isLoading : Boolean = false,
    val successMessage : String? = null,
    val errorMessage : String? = null
)