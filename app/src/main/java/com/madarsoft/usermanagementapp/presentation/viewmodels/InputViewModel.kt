package com.madarsoft.usermanagementapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madarsoft.usermanagementapp.domain.dataHandler.DataHandler
import com.madarsoft.usermanagementapp.domain.model.UserModel
import com.madarsoft.usermanagementapp.domain.useCases.InsertUserUseCase
import com.madarsoft.usermanagementapp.presentation.states.InputUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InputUiState())
    val uiState: StateFlow<InputUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateAge(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun updateJobTitle(jobTitle: String) {
        _uiState.value = _uiState.value.copy(jobTitle = jobTitle)
    }

    fun updateGender(gender: String) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun saveUser() {
        val state = _uiState.value

        if (validateInput(state)) {
            viewModelScope.launch {
                _uiState.value = state.copy(isLoading = true, errorMessage = null)

                val user = UserModel(
                    name = state.name,
                    age = state.age.toInt(),
                    jobTitle = state.jobTitle,
                    gender = state.gender
                )

                when (val result = insertUserUseCase.invoke(user)) {
                    is DataHandler.Success -> {
                        _uiState.value = InputUiState(isSuccess = true)
                    }

                    is DataHandler.Error -> {
                        _uiState.value = state.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Unknown error occurred"
                        )
                    }

                    is DataHandler.Loading -> {
                        // Already handled above
                    }
                }
            }
        }
    }

    private fun validateInput(state: InputUiState): Boolean {
        return when {
            state.name.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "Name is required")
                false
            }

            state.age.isBlank() || state.age.toIntOrNull() == null -> {
                _uiState.value = state.copy(errorMessage = "Valid age is required")
                false
            }

            state.jobTitle.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "Job title is required")
                false
            }

            state.gender.isBlank() -> {
                _uiState.value = state.copy(errorMessage = "Gender is required")
                false
            }

            else -> true
        }
    }

    fun clearSuccess() {
        _uiState.value = InputUiState()
    }
}