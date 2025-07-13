package com.loanmanagementapp.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.core.event.UiEvent
import com.loanmanagementapp.core.exception.LoanManagementException
import com.loanmanagementapp.utils.resource.ResourceProvider
import com.loanmanagementapp.core.network.ApiException
import com.loanmanagementapp.core.network.RestResult
import com.loanmanagementapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class CoreViewModel(
    private val eventPublisher: DefaultUiEventPublisher,
    open val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvent>()
    val eventsFlow: SharedFlow<UiEvent> = _eventsFlow.asSharedFlow()

    private var isLoadingShown = false

    fun <T> executeUseCase(
        result: Flow<RestResult<T>>,
        onSuccess: (suspend (T) -> Unit),
        onFailure: (suspend (throwable: LoanManagementException) -> Unit) = { renderSiTaxiError(it) }
    ) {
        viewModelScope.launch {
            result.collectLatest { result ->
                when (result) {
                    is RestResult.Success -> {
                        onSuccess(result.data)
                    }

                    is RestResult.Failure -> {
                        when (val throwable = result.throwable) {
                            is LoanManagementException -> onFailure.invoke(throwable)
                            is ApiException -> renderApiError(throwable)
                            else -> renderUnhandledError(throwable)
                        }

                    }

                    is RestResult.Loading -> {
                        handleLoading(result.isShowing)
                    }
                }
            }
        }
    }

    open fun renderUnhandledError(error: Throwable?) {
        error?.let {
            eventPublisher.publishUiEvent(UiEvent.ErrorEvent(resourceProvider.getString(R.string.generic_error)))
        }

        Log.e(this::class.java.name, error?.message.toString())

    }

    open fun renderApiError(error: ApiException) {
        eventPublisher.publishUiEvent(
            UiEvent.ErrorEvent(
                message = error.detail ?: resourceProvider.getString(R.string.generic_error)
            )
        )

        Log.e(this::class.java.name, error.message.toString())

    }

    open fun renderSiTaxiError(error: Throwable?) {
        error?.let {
            eventPublisher.publishUiEvent(
                UiEvent.ErrorEvent(
                    message = error.message ?: resourceProvider.getString(
                        R.string.generic_error
                    )
                )
            )
        }

        Log.e(this::class.java.name, error?.message.toString())

    }


    open fun handleLoading(showLoading: Boolean) {
        if (isLoadingShown != showLoading) {
            isLoadingShown = showLoading
            eventPublisher.publishUiEvent(UiEvent.LoadingEvent(showLoading))
        }
    }

    fun sendErrorEvent(event: UiEvent.ErrorEvent) {
        eventPublisher.publishUiEvent(event)
    }
}
