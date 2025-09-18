package com.madarsoft.usermanagementapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.useCases.DeleteUserUseCase
import com.madarsoft.usermanagementapp.domain.useCases.GetUsersUseCase
import com.madarsoft.usermanagementapp.presentation.states.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayUsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getUsersUseCase().collect { users ->
                _uiState.value = _uiState.value.copy(
                    users = users,
                    isLoading = false
                )
            }
        }
    }

    fun deleteUser(user: UserModel) {
        viewModelScope.launch {
            when (val result = deleteUserUseCase.invoke(user)) {
                is DataHandler.Success -> {
                    // Users will be automatically updated through the Flow
                }
                is DataHandler.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.exception.message ?: "Failed to delete user"
                    )
                }
                is DataHandler.Loading -> {
                    // Handle loading if needed
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}