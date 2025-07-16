package com.loanmanagementapp.ui.home

import com.loanmanagementapp.core.base.CoreViewModel
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.domain.usecase.LoginUseCase
import com.loanmanagementapp.utils.resource.ResourceProvider
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    eventPublisher: DefaultUiEventPublisher,
    resourceProvider: ResourceProvider,
    private val loginUseCase: LoginUseCase
) : CoreViewModel(eventPublisher, resourceProvider) {
}