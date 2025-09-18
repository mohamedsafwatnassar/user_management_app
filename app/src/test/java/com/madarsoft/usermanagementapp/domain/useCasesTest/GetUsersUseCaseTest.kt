package com.madarsoft.usermanagementapp.domain.useCasesTest

import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.repository.UserRepository
import com.madarsoft.usermanagementapp.domain.useCases.GetUsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetUsersUseCaseTest {

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `invoke should return domain users from repository`() = runTest {
        // Given
        val userModels = listOf(
            UserModel(1, "John", 30, "Developer", "Male"),
            UserModel(2, "Jane", 25, "Designer", "Female")
        )
        whenever(repository.getAllUsers()).thenReturn(flowOf(userModels))

        // When
        val result = getUsersUseCase().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("John", result[0].name)
        assertEquals("Jane", result[1].name)
        assertTrue(result[0] is UserModel)
    }
}