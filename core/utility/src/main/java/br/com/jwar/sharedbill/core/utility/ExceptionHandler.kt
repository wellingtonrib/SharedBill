package br.com.jwar.sharedbill.core.utility

interface ExceptionHandler {
    fun recordException(e: Exception)
}