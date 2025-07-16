package com.loanmanagementapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.loanmanagementapp.ui.home.HomeScreen
import com.loanmanagementapp.ui.login.LoginScreen
import com.loanmanagementapp.ui.register.RegisterScreen
import com.loanmanagementapp.ui.theme.black

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(500)
            )
        }
    ) {
        composable<Destination.Empty> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(black),
            )
        }

        composable<Destination.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Destination.Home) {
                        popUpTo<Destination.Onboarding> { inclusive = true }
                    }
                },
                navController = navController
            )
        }
        composable<Destination.Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destination.Home) {
                        popUpTo<Destination.Onboarding> { inclusive = true }
                    }
                }
            )
        }
        composable<Destination.Home> {
            HomeScreen()
        }
    }
}