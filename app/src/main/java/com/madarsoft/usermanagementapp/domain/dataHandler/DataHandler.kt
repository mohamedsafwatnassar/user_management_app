package com.madarsoft.usermanagementapp.domain.dataHandler

// Data Handler - Sealed class for handling data states
sealed class DataHandler<out T> {
    data class Success<out T>(val data: T) : DataHandler<T>()
    data class Error(val exception: Throwable) : DataHandler<Nothing>()
    data class Loading(val isLoading: Boolean = true) : DataHandler<Nothing>()
}