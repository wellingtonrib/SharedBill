package br.com.jwar.sharedbill.domain.model

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    inline fun onSuccess(block: (R) -> Unit): Result<R> {
        if (this is Success) block(data)
        return this
    }

    inline fun <T> mapSuccess(transform: (R) -> T): Result<T> =
        when (this) {
            is Success -> Success(transform(data))
            is Error -> Error(exception)
        }

    fun getOrNull() = if (this is Success) data else null
}

val Result<*>.isSuccessful
    get() = this is Result.Success && data != null