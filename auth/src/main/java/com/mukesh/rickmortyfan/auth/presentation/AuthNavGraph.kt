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
                onLoginSuccess = onLoginSuccess,
                onForgotPasswordClick = {
                },
                onSignUpClick = {
                    navController.navigate(SignUpRoute)
                }
            )
        }
        composable<SignUpRoute> {
            SignUpScreen(
                onLoginSuccess = onLoginSuccess,
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
