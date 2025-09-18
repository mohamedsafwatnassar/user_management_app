package com.madarsoft.usermanagementapp.data.repositoryImpl

import com.madarsoft.usermanagementapp.data.dio.UserDao
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.mapper.toEntity
import com.madarsoft.usermanagementapp.domain.mapper.toModel
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


// Repository Implementation
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override fun getAllUsers(): Flow<List<UserModel>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun insertUser(user: UserModel): DataHandler<Unit> {
        return try {
            userDao.insertUser(user.toEntity())
            DataHandler.Success(Unit)
        } catch (e: Exception) {
            DataHandler.Error(e)
        }
    }

    override suspend fun deleteUser(user: UserModel): DataHandler<Unit> {
        return try {
            userDao.deleteUser(user.toEntity())
            DataHandler.Success(Unit)
        } catch (e: Exception) {
            DataHandler.Error(e)
        }
    }
}