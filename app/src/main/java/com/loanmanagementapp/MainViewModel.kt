package com.loanmanagementapp

import com.loanmanagementapp.core.base.CoreViewModel
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.navigation.Destination
import com.loanmanagementapp.utils.resource.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    eventPublisher: DefaultUiEventPublisher,
    override val resourceProvider: ResourceProvider,
    //private val autManager: AuthManager,
) : CoreViewModel(eventPublisher, resourceProvider) {

    private val _initialDestination = MutableStateFlow<Destination>(Destination.Login)
    val initialDestination: StateFlow<Destination> = _initialDestination
}