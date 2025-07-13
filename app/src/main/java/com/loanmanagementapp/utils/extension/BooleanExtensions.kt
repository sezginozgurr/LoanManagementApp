package com.loanmanagementapp.utils.extension

fun Boolean?.orFalse() = this ?: false
fun Boolean?.orTrue() = this ?: true