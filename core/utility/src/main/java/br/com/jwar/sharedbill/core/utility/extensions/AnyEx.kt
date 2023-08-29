package br.com.jwar.sharedbill.core.utility.extensions

fun <T: Any> ifOrNull(condition: Boolean, ifTrue: () -> T) =
    if (condition) ifTrue() else null