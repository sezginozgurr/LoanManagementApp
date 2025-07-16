package com.loanmanagementapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loanmanagementapp.R
import com.loanmanagementapp.core.components.PrimaryButton
import com.loanmanagementapp.core.components.SitTextField
import com.loanmanagementapp.navigation.Destination
import com.loanmanagementapp.utils.extension.collectFlow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    navController: NavController //gereksiz fakat 2 türlü yönlendirmede gösterilmek istenmiştir
) {
    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    collectFlow(viewModel.state) { uiEvent ->
        if (state.isSuccess) {
            onLoginSuccess
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        SitTextField(
            value = email,
            title = stringResource(R.string.login_email),
            onValueChange = { email = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 12.dp)
        )

        SitTextField(
            value = password,
            title = stringResource(R.string.login_password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 24.dp)
        )

        PrimaryButton(
            text = "Login",
            enabled = true,
            actionClickListener = {
                viewModel.login(email, password)
            }
        )

        Spacer(modifier = Modifier.padding(top = 24.dp))

        PrimaryButton(
            text = stringResource(R.string.login_register_button_text),
            enabled = true,
            actionClickListener = {
                navController.navigate(Destination.Register)
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
}
