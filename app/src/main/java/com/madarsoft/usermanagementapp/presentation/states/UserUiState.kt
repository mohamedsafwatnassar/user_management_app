package com.madarsoft.usermanagementapp.presentation.states

import com.madarsoft.usermanagementapp.domain.model.UserModel

// UI State
data class UserUiState(
    val users: List<UserModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)