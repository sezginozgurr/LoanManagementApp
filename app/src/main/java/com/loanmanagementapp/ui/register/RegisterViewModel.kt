package com.loanmanagementapp.ui.register

import com.loanmanagementapp.core.base.CoreViewModel
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.domain.usecase.RegisterUseCase
import com.loanmanagementapp.utils.resource.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    eventPublisher: DefaultUiEventPublisher,
    resourceProvider: ResourceProvider,
    private val registerUseCase: RegisterUseCase
) : CoreViewModel(eventPublisher, resourceProvider) {

    private val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state.asStateFlow()

    fun register(email: String, password: String, phone: String) {
        executeUseCase(
            registerUseCase(email, password, phone),
            onSuccess = { data ->
                _state.update { it.copy(isSuccess = true) }
                Timber.tag("RegisterViewModel").e("Kayıt başarılı: $data")
                // _uiEvent.emit(RegisterUiEvent.NavigateHome)
            },
            onFailure = {
                _state.update { it.copy(isError = true, errorMessage = "Kayıt Başarısız") }
            }
        )
    }
}