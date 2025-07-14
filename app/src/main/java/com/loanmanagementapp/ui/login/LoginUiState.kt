package com.loanmanagementapp.ui.login

data class LoginUiState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)