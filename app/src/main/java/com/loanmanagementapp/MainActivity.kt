package com.loanmanagementapp

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.loanmanagementapp.core.components.GenericLoadingScreen
import com.loanmanagementapp.core.components.SitScaffold
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.core.event.UiEvent
import com.loanmanagementapp.navigation.NavigationGraph
import com.loanmanagementapp.ui.theme.LoanManagementAppTheme
import com.loanmanagementapp.utils.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var eventPublisher: DefaultUiEventPublisher

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoanManagementAppTheme {
                var isLoading by remember { mutableStateOf(false) }
                SitScaffold {
                    val navController = rememberNavController()
                    val initialDestination =
                        viewModel.initialDestination.collectAsStateWithLifecycle().value

                    NavigationGraph(
                        navController = navController,
                        startDestination = initialDestination,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                collectFlow(eventPublisher.observeUiEvent()) { event ->
                    when (event) {
                        is UiEvent.LoadingEvent -> {
                            isLoading = event.showLoading
                        }

                        is UiEvent.ErrorEvent -> {
                            Toast.makeText(
                                this,
                                "Alınan Hata Mesajı; ${event.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        UiEvent.NavigateToLogin -> {}
                    }
                }
                if (isLoading) {
                    GenericLoadingScreen()
                }
            }
        }
    }
}
