package com.loanmanagementapp.domain.repository

import com.loanmanagementapp.core.network.RestResult
import com.loanmanagementapp.domain.model.LoginUiModel
import com.loanmanagementapp.domain.model.RegisterUiModel

interface LoanManagementRepository {

    suspend fun register(
        email: String,
        password: String,
        phone: String
    ): RestResult<RegisterUiModel>

    suspend fun login(
        email: String,
        password: String
    ): RestResult<LoginUiModel>

}