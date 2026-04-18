package com.mukesh.rickmortyfan.auth.presentation

import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import com.mukesh.rickmortyfan.auth.domain.use_cases.LoginUseCase
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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val loginUseCase: LoginUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val sampleUser = RickMortyUser(
        name = "Rick Sanchez",
        email = "test@example.com",
        profileImageUrl = null,
        id = "123"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login should emit success state when use case returns success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        every { loginUseCase(email, password) } returns flowOf(Resource.Success(sampleUser))

        // When
        viewModel.login(email, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state.isSuccess)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
    }

    @Test
    fun `login should emit error state when email is empty`() = runTest {
        // When
        viewModel.login("", "password123")

        // Then
        val state = viewModel.state.value
        assertEquals("Email cannot be empty", state.error)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `login should emit error state when use case returns error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Invalid credentials"
        every { loginUseCase(email, password) } returns flowOf(Resource.Error(errorMessage))

        // When
        viewModel.login(email, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(errorMessage, state.error)
        assertEquals(false, state.isLoading)
    }
}
