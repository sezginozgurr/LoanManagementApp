package com.loanmanagementapp.data.repository

import com.loanmanagementapp.data.mapper.LoanManagementMapper
import com.loanmanagementapp.domain.repository.LoanManagementRepository
import javax.inject.Inject

class LoanManagementRepositoryImpl @Inject constructor(
    private val mapper: LoanManagementMapper
) : LoanManagementRepository {

}