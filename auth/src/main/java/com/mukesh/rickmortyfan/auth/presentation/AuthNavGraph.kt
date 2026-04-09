package com.mukesh.rickmortyfan.auth.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

@Serializable
object LoginRoute

@Serializable
object SignUpRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    navigation<AuthGraph>(
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginClick = { email, password ->
                    onLoginSuccess()
                },
                onForgotPasswordClick = {
                    // Navigate to forgot password if available
                },
                onSignUpClick = {
                    navController.navigate(SignUpRoute)
                }
            )
        }
        composable<SignUpRoute> {
            SignUpScreen(
                onSignUpClick = { email, password ->
                    // Handle sign up logic
                    onLoginSuccess()
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
