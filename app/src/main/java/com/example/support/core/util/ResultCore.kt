package com.example.support.core.util

sealed class ResultCore<out T> {
    data class Success<out T>(val data: T) : ResultCore<T>()
    data class Failure(val message: String) : ResultCore<Nothing>()
}
