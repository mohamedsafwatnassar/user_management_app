package com.madarsoft.usermanagementapp.domain.useCasesTest

import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.repository.UserRepository
import com.madarsoft.usermanagementapp.domain.useCases.InsertUserUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class InsertUserUseCaseTest {

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var insertUserUseCase: InsertUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        insertUserUseCase = InsertUserUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository succeeds`() = runTest {
        // Given
        val user = UserModel(0, "John", 30, "Developer", "Male")
        whenever(repository.insertUser(any())).thenReturn(DataHandler.Success(Unit))

        // When
        val result = insertUserUseCase(user)

        // Then
        assertTrue(result is DataHandler.Success)
    }

    @Test
    fun `invoke should return error when repository fails`() = runTest {
        // Given
        val user = UserModel(0, "John", 30, "Developer", "Male")
        val exception = RuntimeException("Database error")
        whenever(repository.insertUser(any())).thenReturn(DataHandler.Error(exception))

        // When
        val result = insertUserUseCase(user)

        // Then
        assertTrue(result is DataHandler.Error)
    }
}