package com.loanmanagementapp.domain.usecase

import com.loanmanagementapp.domain.repository.LoanManagementRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadManagementUseCase @Inject constructor(
    private val repository: LoanManagementRepository,
    private val dispatcher: CoroutineDispatcher
) {
    /* operator fun invoke(token: String): Flow<RestResult<*>> {
        return flow {
            val response = repository.accountStatus(token)
            emit(response)
        }.buildFlow(dispatcher)
    } */
}