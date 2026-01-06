package com.example.learning1.presentation

sealed interface LoginEffects {
    data class OnError(val message : String) : LoginEffects
    data class OnSuccess(val message : String) : LoginEffects
}