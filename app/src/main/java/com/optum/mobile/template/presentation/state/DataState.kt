package com.optum.mobile.template.presentation.state

open class DataState<out T> constructor(
    val status: Status = Status.LOADING,
    val data: T?,
    val message: String?
) {

    fun <T> success(data: T): DataState<T> {
        return DataState(
            Status.SUCCESS,
            data,
            null
        )
    }

    fun <T> error(message: String, data: T?): DataState<T> {
        return DataState(
            Status.ERROR,
            data,
            message
        )
    }

    fun <T> loading(): DataState<T> {
        return DataState(
            Status.LOADING,
            null,
            null
        )
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}