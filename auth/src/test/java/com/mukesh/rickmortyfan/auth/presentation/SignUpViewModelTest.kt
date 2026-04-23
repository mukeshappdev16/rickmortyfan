package com.mukesh.rickmortyfan.auth.presentation

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import com.mukesh.rickmortyfan.auth.domain.use_cases.SignUpUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {
    private lateinit var viewModel: SignUpViewModel
    private val signUpUseCase: SignUpUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val sampleUser =
        RickMortyUser(
            name = "New User",
            email = "signup@example.com",
            profileImageUrl = null,
            id = "456",
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(signUpUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signUp should emit success state when use case returns success`() =
        runTest {
            // Given
            val email = "signup@example.com"
            val password = "password123"
            every { signUpUseCase(email, password) } returns flowOf(Resource.Success(sampleUser))

            // When
            viewModel.signUp(email, password)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertTrue(state.isSuccess)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }

    @Test
    fun `signUp should emit error state when email is empty`() =
        runTest {
            // When
            viewModel.signUp("", "password123")

            // Then
            val state = viewModel.state.value
            assertEquals("Email cannot be empty", state.error)
            assertEquals(false, state.isLoading)
        }

    @Test
    fun `signUp should emit error state when use case returns error`() =
        runTest {
            // Given
            val email = "signup@example.com"
            val password = "password123"
            val errorMessage = "Email already in use"
            every { signUpUseCase(email, password) } returns flowOf(Resource.Error(errorMessage))

            // When
            viewModel.signUp(email, password)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
}
