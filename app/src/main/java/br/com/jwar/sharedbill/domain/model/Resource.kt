package br.com.jwar.sharedbill.domain.model

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val throwable: Throwable): Resource<Nothing>()
}