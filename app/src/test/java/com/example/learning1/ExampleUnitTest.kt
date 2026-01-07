package com.example.learning1

import com.example.learning1.domain.LoginUseCase
import com.example.learning1.presentation.LoginEvents
import com.example.learning1.presentation.LoginViewModel
import com.example.learning1.utils.TestDispatcherProvider
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var viewmodel: LoginViewModel


    @Before
    fun init(){
        val loginUseCase : LoginUseCase = mockk()
        val testDispatcher = TestDispatcherProvider()
        viewmodel = LoginViewModel(loginUseCase,testDispatcher)
    }

    @Test
    fun `username state change check`(){
         viewmodel.onEvent(LoginEvents.UserNameChangedEvent("abcd"))

        val result = viewmodel.loginState.value.username

        assertEquals("abcd",result)
    }

    @Test
    fun `password state change check`(){
        viewmodel.onEvent(LoginEvents.PasswordChangedEvent("abcd"))

        val result = viewmodel.loginState.value.password

        assertEquals("abcd",result)
    }

    @Test
    fun `username error state check with invalid input`(){
        viewmodel.onEvent(LoginEvents.UserNameChangedEvent("abcd"))
        val result = viewmodel.loginState.value.userNameError
        assertNotNull(result)
    }


    @Test
    fun `password error state check with invalid input`(){
        viewmodel.onEvent(LoginEvents.PasswordChangedEvent("abcd"))
        val result = viewmodel.loginState.value.passwordError
        assertNotNull(result)
    }

    @Test
    fun `username error state check with blank invalid input`(){
        viewmodel.onEvent(LoginEvents.UserNameChangedEvent(""))
        val result = viewmodel.loginState.value.userNameError
        assertNotNull(result)
    }


    @Test
    fun `password error state check with blank invalid input`(){
        viewmodel.onEvent(LoginEvents.PasswordChangedEvent(""))
        val result = viewmodel.loginState.value.passwordError
        assertNotNull(result)
    }



    @Test
    fun `username error state check with valid input`(){
        viewmodel.onEvent(LoginEvents.UserNameChangedEvent("user name"))
        val result = viewmodel.loginState.value.userNameError
        assertNull(result)
    }


    @Test
    fun `password error state check with valid input`(){
        viewmodel.onEvent(LoginEvents.PasswordChangedEvent("password@123"))
        val result = viewmodel.loginState.value.passwordError
        assertNull(result)
    }


    @Test
    fun `login event call with blank invalid input`(){
        val userNameInput = ""
        val passwordNameInput = ""
        viewmodel.onEvent(LoginEvents.LoginEvent(userNameInput,passwordNameInput))
        val userNameErrorResult = viewmodel.loginState.value.userNameError
        val passwordNameErrorResult = viewmodel.loginState.value.passwordError

        println("Given blank input for username and expecting an error - error it generated: $userNameErrorResult")
        println("Given blank input for password and error it generated - error it generated:  $passwordNameErrorResult")
        assertNotNull(userNameErrorResult)
        assertNotNull(userNameErrorResult)

    }






}