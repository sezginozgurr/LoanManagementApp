package com.loanmanagementapp.core.event

import kotlinx.coroutines.flow.SharedFlow

interface DefaultUiEventPublisher {
    fun publishUiEvent(event: UiEvent)
    fun observeUiEvent(): SharedFlow<UiEvent>
}