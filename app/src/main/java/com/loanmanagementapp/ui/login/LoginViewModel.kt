package com.loanmanagementapp.ui.login

import com.loanmanagementapp.core.base.CoreViewModel
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.domain.usecase.LoginUseCase
import com.loanmanagementapp.utils.resource.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    eventPublisher: DefaultUiEventPublisher,
    resourceProvider: ResourceProvider,
    private val loginUseCase: LoginUseCase
) : CoreViewModel(eventPublisher, resourceProvider) {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        executeUseCase(
            loginUseCase(email, password),
            onSuccess = {
                _state.update { it.copy(isSuccess = true) }
            },
            onFailure = {
                _state.update { it.copy(isError = true, errorMessage = "Giriş Başarısız") }
            }
        )
    }

    override fun handleLoading(isShowing: Boolean) {
        _state.update { it.copy(isLoading = isShowing) }
    }
}
