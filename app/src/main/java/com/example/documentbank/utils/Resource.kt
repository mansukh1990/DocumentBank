package com.example.documentbank.utils

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
    val data: T? = null,
    var message: String? = null
) {

    class Empty<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(message: String? = null, data: T? = null) :
        Resource<T>(data = data, message = message)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$message]"
            is Loading<T> -> "Loading"
            is Empty<T> -> "empty"
        }
    }
}
