package com.example.graphqltrial.utils

sealed class Result<T>(
    val value: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String?, data: T? = null) : Result<T>(data, message)
    class Loading<T> : Result<T>()
}