package com.washuTechnologies.merced.api

/**
 * Sealed class to model the result of an operation.
 */
sealed class Result<out T : Any> {

    /**
     * Operation success containing the [result] of the operation.
     */
    data class Success<out T : Any>(val result: T) : Result<T>()

    /**
     * Operation failure.
     */
    object Loading : Result<Nothing>()

    /**
     * Operation has started but not completed.
     */
    object Error : Result<Nothing>()
}