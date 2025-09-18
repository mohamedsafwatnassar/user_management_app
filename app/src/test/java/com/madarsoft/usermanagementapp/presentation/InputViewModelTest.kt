package com.madarsoft.usermanagementapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.useCases.InsertUserUseCase
import com.madarsoft.usermanagementapp.presentation.viewmodels.InputViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class InputViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var insertUserUseCase: InsertUserUseCase

    private lateinit var viewModel: InputViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = InputViewModel(insertUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateName should update name in uiState`() {
        // When
        viewModel.updateName("John Doe")

        // Then
        assertEquals("John Doe", viewModel.uiState.value.name)
    }

    @Test
    fun `updateAge should update age in uiState`() {
        // When
        viewModel.updateAge("30")

        // Then
        assertEquals("30", viewModel.uiState.value.age)
    }

    @Test
    fun `updateJobTitle should update jobTitle in uiState`() {
        // When
        viewModel.updateJobTitle("Developer")

        // Then
        assertEquals("Developer", viewModel.uiState.value.jobTitle)
    }

    @Test
    fun `updateGender should update gender in uiState`() {
        // When
        viewModel.updateGender("Male")

        // Then
        assertEquals("Male", viewModel.uiState.value.gender)
    }

    @Test
    fun `saveUser should show error when name is empty`() {
        // Given
        viewModel.updateAge("30")
        viewModel.updateJobTitle("Developer")
        viewModel.updateGender("Male")
        // name is empty

        // When
        viewModel.saveUser()

        // Then
        assertEquals("Name is required", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun `saveUser should show error when age is invalid`() {
        // Given
        viewModel.updateName("John")
        viewModel.updateAge("invalid")
        viewModel.updateJobTitle("Developer")
        viewModel.updateGender("Male")

        // When
        viewModel.saveUser()

        // Then
        assertEquals("Valid age is required", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun `saveUser should succeed with valid input`() = runTest {
        // Given
        viewModel.updateName("John")
        viewModel.updateAge("30")
        viewModel.updateJobTitle("Developer")
        viewModel.updateGender("Male")
        whenever(insertUserUseCase(any())).thenReturn(DataHandler.Success(Unit))

        // When
        viewModel.saveUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value.isSuccess)
        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `saveUser should show error when use case fails`() = runTest {
        // Given
        viewModel.updateName("John")
        viewModel.updateAge("30")
        viewModel.updateJobTitle("Developer")
        viewModel.updateGender("Male")
        val exception = RuntimeException("Database error")
        whenever(insertUserUseCase(any())).thenReturn(DataHandler.Error(exception))

        // When
        viewModel.saveUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertFalse(viewModel.uiState.value.isSuccess)
        assertEquals("Database error", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
    }
}