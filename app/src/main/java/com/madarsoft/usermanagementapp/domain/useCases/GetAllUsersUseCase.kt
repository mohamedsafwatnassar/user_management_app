package com.madarsoft.usermanagementapp.domain.useCases

import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<UserModel>> =
        repository.getAllUsers().map { userModels ->
            userModels.map { it }
        }
}