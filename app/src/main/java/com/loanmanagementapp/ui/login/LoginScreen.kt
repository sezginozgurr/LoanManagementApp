package com.loanmanagementapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loanmanagementapp.core.components.PrimaryButton
import com.loanmanagementapp.core.components.SitTextField
import com.loanmanagementapp.utils.extension.collectFlow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        SitTextField(
            value = email,
            title = "Email",
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        SitTextField(
            value = password,
            title = "Password",
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        PrimaryButton(
            text = if (state.isLoading) "Logging in..." else "Login",
            enabled = !state.isLoading,
            actionClickListener = {
                viewModel.login(email, password)
            }
        )

        if (state.isError) {
            Text(
                text = state.errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    collectFlow(viewModel.state) { uiEvent ->
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }
}
