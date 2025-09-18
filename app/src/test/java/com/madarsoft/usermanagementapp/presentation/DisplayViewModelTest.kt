package com.madarsoft.usermanagementapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.useCases.DeleteUserUseCase
import com.madarsoft.usermanagementapp.domain.useCases.GetUsersUseCase
import com.madarsoft.usermanagementapp.presentation.viewmodels.DisplayUsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DisplayViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Mock
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    private lateinit var viewModel: DisplayUsersViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel should load users on initialization`() = runTest {
        // Given
        val users = listOf(
            UserModel(1, "John", 30, "Developer", "Male"),
            UserModel(2, "Jane", 25, "Designer", "Female")
        )
        whenever(getUsersUseCase()).thenReturn(flowOf(users))

        // When
        viewModel = DisplayUsersViewModel(getUsersUseCase, deleteUserUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(2, viewModel.uiState.value.users.size)
        assertEquals("John", viewModel.uiState.value.users[0].name)
        assertEquals("Jane", viewModel.uiState.value.users[1].name)
    }

    @Test
    fun `deleteUser should succeed when use case succeeds`() = runTest {
        // Given
        val users = listOf(UserModel(1, "John", 30, "Developer", "Male"))
        whenever(getUsersUseCase()).thenReturn(flowOf(users))
        whenever(deleteUserUseCase(any())).thenReturn(DataHandler.Success(Unit))
        viewModel = DisplayUsersViewModel(getUsersUseCase, deleteUserUseCase)

        val userToDelete = UserModel(1, "John", 30, "Developer", "Male")

        // When
        viewModel.deleteUser(userToDelete)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `deleteUser should show error when use case fails`() = runTest {
        // Given
        val users = listOf(UserModel(1, "John", 30, "Developer", "Male"))
        whenever(getUsersUseCase()).thenReturn(flowOf(users))
        val exception = RuntimeException("Delete failed")
        whenever(deleteUserUseCase(any())).thenReturn(DataHandler.Error(exception))
        viewModel = DisplayUsersViewModel(getUsersUseCase, deleteUserUseCase)

        val userToDelete = UserModel(1, "John", 30, "Developer", "Male")

        // When
        viewModel.deleteUser(userToDelete)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals("Delete failed", viewModel.uiState.value.errorMessage)
    }
}