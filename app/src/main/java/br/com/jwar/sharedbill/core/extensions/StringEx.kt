package br.com.jwar.sharedbill.core.extensions

import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_SMALL = "dd/MM"
const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
const val ZERO = "0"

fun Timestamp.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    this.toDate().format(pattern)

fun Date.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    SimpleDateFormat(pattern).format(this)

fun String.parse(pattern: String = DATE_FORMAT_DEFAULT, default: Date = Date()): Date =
    SimpleDateFormat(pattern).parse(this) ?: default

fun String.toCurrency() = this.toBigDecimalOrZero().toCurrency()

fun String.toBigDecimalOrZero(): BigDecimal =
    try { this.toBigDecimalOrNull().orZero() } catch (e: NumberFormatException) { BigDecimal.ZERO }

fun String?.orZero() = if (this.isNullOrBlank()) ZERO else this

fun String.replaceIf(replacement: String, predicate: () -> Boolean) =
    if (predicate()) replacement else this

//todo move extensions to appropriate module