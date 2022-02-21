package com.washuTechnologies.merced.api

/**
 * Sealed class to model the result of an operation.
 */
sealed class Result<out T>(val result: T?) {

    /**
     * Operation success containing [result].
     */
    class Success<out T>(data: T) : Result<T>(data)

    /**
     * Operation failure.
     */
    class Error : Result<Nothing>(null)

    /**
     * Operation has started but not completed.
     */
    class Loading : Result<Nothing>(null)
}