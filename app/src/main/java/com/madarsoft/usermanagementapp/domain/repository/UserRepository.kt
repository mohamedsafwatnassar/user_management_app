package com.madarsoft.usermanagementapp.domain.repository

import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<UserModel>>
    suspend fun insertUser(user: UserModel): DataHandler<Unit>
    suspend fun deleteUser(user: UserModel): DataHandler<Unit>
}