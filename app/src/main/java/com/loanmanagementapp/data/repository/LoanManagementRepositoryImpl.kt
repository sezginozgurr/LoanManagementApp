package com.loanmanagementapp.data.repository

import com.loanmanagementapp.core.network.RestResult
import com.loanmanagementapp.data.mapper.LoanManagementMapper
import com.loanmanagementapp.domain.repository.LoanManagementRepository
import com.loanmanagementapp.manager.FirebaseAuthManager
import com.loanmanagementapp.manager.FirestoreManager
import com.loanmanagementapp.data.model.response.LoginResponse
import com.loanmanagementapp.domain.model.LoginUiModel
import com.loanmanagementapp.data.model.response.RegisterResponse
import com.loanmanagementapp.domain.model.RegisterUiModel
import com.loanmanagementapp.manager.UserDto
import com.loanmanagementapp.utils.extension.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanManagementRepositoryImpl @Inject constructor(
    private val mapper: LoanManagementMapper,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val firestoreManager: FirestoreManager,
    private val dispatcher: CoroutineDispatcher,
) : LoanManagementRepository {

    override suspend fun register(
        email: String,
        password: String,
        phone: String
    ): RestResult<RegisterUiModel> =
        withContext(dispatcher) {
            return@withContext safeApiCall {
                val authResult = firebaseAuthManager.register(email, password)
                val uid = authResult.user?.uid ?: throw Exception("UID null")

                firestoreManager.saveUser(uid, UserDto(email, phone))
                RegisterResponse(
                    uid = uid,
                    email = email,
                    phone = phone
                )
            }.map {
                mapper.mapToRegisterUiModel(it)
            }
        }

    override suspend fun login(
        email: String,
        password: String
    ): RestResult<LoginUiModel> =
        withContext(dispatcher) {
            return@withContext safeApiCall {
                val authResult = firebaseAuthManager.login(email, password)
                val uid = authResult.user?.uid ?: throw Exception("UID null")

                val userDto = firestoreManager.getUser(uid)

                LoginResponse(
                    uid = uid,
                    email = userDto?.email.orEmpty(),
                    phone = userDto?.phone.orEmpty()
                )
            }.map {
                mapper.mapToLoginUiModel(it)
            }
        }

}