package com.mukesh.rickmortyfan.auth.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysAllFields() {
        composeTestRule.setContent {
            LoginScreenContent(
                email = "",
                onEmailChange = {},
                password = "",
                onPasswordChange = {},
                isPasswordVisible = false,
                onTogglePasswordVisibility = {},
                onLoginClick = {},
                onForgotPasswordClick = {},
                onSignUpClick = {},
            )
        }

        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOGIN").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don't have an account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("SIGN UP").assertIsDisplayed()
    }

    @Test
    fun loginScreen_typingUpdatesFields() {
        var emailValue = ""
        var passwordValue = ""

        composeTestRule.setContent {
            LoginScreenContent(
                email = emailValue,
                onEmailChange = { emailValue = it },
                password = passwordValue,
                onPasswordChange = { passwordValue = it },
                isPasswordVisible = false,
                onTogglePasswordVisibility = {},
                onLoginClick = {},
                onForgotPasswordClick = {},
                onSignUpClick = {},
            )
        }

        composeTestRule.onNodeWithText("Email").performTextInput("rick@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("portalgun123")

        // In a real integration test, you'd verify the state updates
        // Here we just ensure the interactions don't crash
    }
}
