package com.loanmanagementapp.utils.extension

import com.loanmanagementapp.core.exception.LoanManagementException
import com.loanmanagementapp.core.network.ApiException
import com.loanmanagementapp.core.network.RestResponse
import com.loanmanagementapp.core.network.RestResult
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCallWithRestResponse(call: suspend () -> RestResponse<T>): RestResult<T> {
    return try {
        val response = call()
        if (response.data != null)
            RestResult.Success(response.data)
        else {
            RestResult.Failure(
                LoanManagementException(
                    response.message.orEmpty(),
                    response.code.orZero()
                )
            )
        }

    } catch (e: HttpException) {
        RestResult.Failure(throwable = extractErrorResponse(e) ?: e)
    }
}

suspend fun <T> safeApiCallWithResponse(call: suspend () -> Response<T>): RestResult<T> {
    return try {
        val response = call()


        if (response.isSuccessful && response.body() != null) {
            RestResult.Success(response.body()!!)
        } else {
            RestResult.Failure(
                LoanManagementException(
                    response.message().orEmpty(),
                    response.code()
                )
            )
        }

    } catch (e: HttpException) {
        // HttpException: HTTP hataları (örn: 404, 500)
        RestResult.Failure(throwable = e)

    } catch (ex: Exception) {
        // Diğer genel hatalar
        RestResult.Failure(throwable = ex)
    }
}

private fun extractErrorResponse(httpException: HttpException): ApiException? {
    return try {
        val errorBody = httpException.response()?.errorBody()?.string()
        if (errorBody != null) {
            Gson().fromJson(errorBody, ApiException::class.java)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
