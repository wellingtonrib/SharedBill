package br.com.jwar.sharedbill.core.extensions

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

const val DATE_FORMAT_SMALL = "dd/MM"
const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
const val ZERO = "0"

fun Timestamp.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    this.toDate().format(pattern)

fun Date.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    SimpleDateFormat(pattern).format(this)

fun String.parse(pattern: String = DATE_FORMAT_DEFAULT, default: Date = Date()): Date =
    SimpleDateFormat(pattern).parse(this) ?: default

fun String.toCurrency() =
    this.toBigDecimal().toCurrency()

fun String?.orZero() =
    this.ifNullOrBlank { ZERO }

fun String?.ifNullOrBlank(block: () -> String) =
    if (this.isNullOrBlank()) block() else this

fun String.replaceIf(replacement: String, predicate: () -> Boolean) =
    if (predicate()) replacement else this