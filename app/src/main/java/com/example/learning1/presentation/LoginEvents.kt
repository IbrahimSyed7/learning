package com.example.learning1.presentation

sealed class LoginEvents {

    data class LoginEvent(val username : String, val password : String) : LoginEvents()
    data class UserNameChangedEvent(val userName : String) : LoginEvents()
    data class PasswordChangedEvent(val password : String) : LoginEvents()

}