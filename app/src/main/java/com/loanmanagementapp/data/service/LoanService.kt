package com.loanmanagementapp.data.service

import android.content.Context
import com.loanmanagementapp.data.model.response.Loan

interface LoanService {
    suspend fun loadLoans(context: Context): List<Loan>
    suspend fun saveLoans(loans: List<Loan>)
}