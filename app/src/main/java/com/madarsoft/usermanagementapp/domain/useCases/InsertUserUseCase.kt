package com.madarsoft.usermanagementapp.domain.useCases

import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): DataHandler<Unit> =
        repository.insertUser(user)
}