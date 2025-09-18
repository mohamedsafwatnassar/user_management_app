package com.madarsoft.usermanagementapp.data

import com.madarsoft.usermanagementapp.data.dio.UserDao
import com.madarsoft.usermanagementapp.data.entity.UserEntity
import com.madarsoft.usermanagementapp.data.repositoryImpl.UserRepositoryImpl
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.kotlin.any

class UserRepositoryImplTest {

    @Mock
    private lateinit var userDao: UserDao

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = UserRepositoryImpl(userDao)
    }

    @Test
    fun `getAllUsers should return mapped user models`() = runTest {
        // Given
        val userEntities = listOf(
            UserEntity(1, "John", 30, "Developer", "Male"),
            UserEntity(2, "Jane", 25, "Designer", "Female")
        )
        whenever(userDao.getAllUsers()).thenReturn(flowOf(userEntities))

        // When
        val result = repository.getAllUsers().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("John", result[0].name)
        assertEquals("Jane", result[1].name)
    }

    @Test
    fun `insertUser should return success when dao succeeds`() = runTest {
        // Given
        val userModel = UserModel(0, "John", 30, "Developer", "Male")

        // When
        val result = repository.insertUser(userModel)

        // Then
        assertTrue(result is DataHandler.Success)
        verify(userDao).insertUser(any())
    }

    @Test
    fun `insertUser should return error when dao throws exception`() = runTest {
        // Given
        val userModel = UserModel(0, "John", 30, "Developer", "Male")
        val exception = RuntimeException("Database error")
        whenever(userDao.insertUser(any())).thenThrow(exception)

        // When
        val result = repository.insertUser(userModel)

        // Then
        assertTrue(result is DataHandler.Error)
        assertEquals(exception, (result as DataHandler.Error).exception)
    }

    @Test
    fun `deleteUser should return success when dao succeeds`() = runTest {
        // Given
        val userModel = UserModel(1, "John", 30, "Developer", "Male")

        // When
        val result = repository.deleteUser(userModel)

        // Then
        assertTrue(result is DataHandler.Success)
        verify(userDao).deleteUser(any())
    }
}