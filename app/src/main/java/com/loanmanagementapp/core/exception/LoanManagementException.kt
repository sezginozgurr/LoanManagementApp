package com.loanmanagementapp.core.exception

data class LoanManagementException(
    override val message: String,
    val errorCode: Int = -1
) : Throwable()