package com.haxio.ozzo.core

sealed class DataState<T>(
    val message: String? = null,
    var data: T? = null
) {
    class Loading<T> : DataState<T>()

    class Success<T>(data: T) : DataState<T>(data=data)

    class Error<T>(message: String?): DataState<T>(message)


}