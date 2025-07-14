package com.loanmanagementapp.domain.usecase

import com.loanmanagementapp.core.network.RestResult
import com.loanmanagementapp.domain.model.RegisterUiModel
import com.loanmanagementapp.domain.repository.LoanManagementRepository
import com.loanmanagementapp.utils.extension.buildFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: LoanManagementRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        email: String,
        password: String,
        phone: String
    ): Flow<RestResult<RegisterUiModel>> {
        return flow {
            val result = repository.register(email, password, phone)
            emit(result)
        }.buildFlow(dispatcher)
    }
}
