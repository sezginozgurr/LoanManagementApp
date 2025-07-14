package com.loanmanagementapp.data.mapper

import com.loanmanagementapp.data.model.response.LoginResponse
import com.loanmanagementapp.domain.model.LoginUiModel
import com.loanmanagementapp.data.model.response.RegisterResponse
import com.loanmanagementapp.domain.model.RegisterUiModel
import com.loanmanagementapp.utils.resource.ResourceProvider
import javax.inject.Inject

class LoanManagementMapper @Inject constructor(
    val resourceProvider: ResourceProvider,
) {

    fun mapToRegisterUiModel(response: RegisterResponse): RegisterUiModel {
        return RegisterUiModel(
            uid = response.uid,
            email = response.email,
            phone = response.phone
        )
    }

    fun mapToLoginUiModel(response: LoginResponse): LoginUiModel {
        return LoginUiModel(
            uid = response.uid,
            email = response.email,
            phone = response.phone
        )
    }

}

