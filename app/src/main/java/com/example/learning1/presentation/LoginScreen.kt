package com.example.learning1.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    val state = viewModel.loginState.collectAsStateWithLifecycle()


    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
       viewModel.loginEffects.collect {
           when(it){
               is LoginEffects.OnError -> {
                   Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
               }
               is LoginEffects.OnSuccess -> {
                   Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
               }
           }
       }
    }
    LoginView(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5E9F6)),
        state,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
fun LoginView(modifier: Modifier, state: State<LoginStates>, onEvent: (LoginEvents) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            state.value.username, onValueChange = {
                onEvent(LoginEvents.UserNameChangedEvent(it))
            }, enabled = state.value.isLoading.not(), label = {
                Text("Username")
            }, isError = state.value.userNameError != null,
            supportingText = {
                if (state.value.userNameError != null) {
                    Text(state.value.userNameError.toString())
                }
            }
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            state.value.password,
            onValueChange = {
                onEvent(LoginEvents.PasswordChangedEvent(it))
            }, enabled = state.value.isLoading.not(),
            label = {
                Text("Password")
            },
            supportingText = {
                if (state.value.passwordError != null) {
                    Text(state.value.passwordError.toString())
                }
            },
            isError = state.value.passwordError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            enabled = state.value.isLoading.not(),
            onClick = {
                onEvent(LoginEvents.LoginEvent(state.value.username, state.value.password))
            }, shape = RoundedCornerShape(12.dp), colors = ButtonColors(
                containerColor = Color(
                    0xFF3F51B5
                ), contentColor = Color.White,
                disabledContainerColor = Color.Green,
                disabledContentColor = Color.White
            )
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Login", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}