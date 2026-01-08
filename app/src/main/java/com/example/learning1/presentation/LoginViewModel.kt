package com.example.learning1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning1.data.dto.NetworkResponse
import com.example.learning1.domain.LoginUseCase
import com.example.learning1.domain.entity.User
import com.example.learning1.ui.theme.Constants
import com.example.learning1.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginStates())
    val loginState = _loginState.asStateFlow()

    private val _loginEffects = MutableSharedFlow<LoginEffects>(replay = 0, extraBufferCapacity = 1)
    val loginEffects = _loginEffects.asSharedFlow()

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.LoginEvent -> login(event.username, event.password)
            is LoginEvents.PasswordChangedEvent -> {
                _loginState.update {
                    it.copy(
                        password = event.password,
                        passwordError = validatePassword(event.password)
                    )
                }
            }

            is LoginEvents.UserNameChangedEvent -> {
                _loginState.update {
                    it.copy(
                        username = event.userName,
                        userNameError = validateUsername(event.userName)
                    )
                }
            }
        }
    }

    private fun login(userName: String, password: String) {
        val userNameValidation = validateUsername(userName)
        val passwordValidation = validatePassword(password)

        if (userNameValidation != null || passwordValidation != null) {
            _loginState.update {
                it.copy(userNameError = userNameValidation, passwordError = passwordValidation)
            }
            return
        }

        viewModelScope.launch(dispatcherProvider.main()) {
            _loginState.update { it.copy(isLoading = true) }
            val result = loginUseCase.invoke(userName, password)
            when (result) {
                is NetworkResponse.Exception -> {
                    _loginEffects.emit(LoginEffects.OnError("$Constants.exception: ${result.message}"))
                    _loginState.update {
                        it.copy(
                            errorMessage = "Exception: ${result.message}",
                            isLoading = false
                        )
                    }
                }

                is NetworkResponse.NetworkError -> {
                    _loginEffects.emit(LoginEffects.OnError("$Constants.exception ${result.message}"))
                    _loginState.update {
                        it.copy(
                            errorMessage = "${result.statusCode} - ${result.message}",
                            isLoading = false
                        )
                    }
                }

                is NetworkResponse.Success<User> -> {
                    _loginEffects.emit(LoginEffects.OnSuccess(result.response.toString()))

                    _loginState.update {
                        it.copy(
                            successMessage = result.response.toString(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun validateUsername(
        query: String,
    ): String? =
        if (query.isBlank() || (query.length < 5)) Constants.userNameValidationError else null


    private fun validatePassword(
        query: String,
    ): String? =
        if (query.length < 6) (Constants.passwordValidationError) else null

}




