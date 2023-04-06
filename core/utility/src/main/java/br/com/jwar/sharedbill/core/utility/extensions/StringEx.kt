package br.com.jwar.sharedbill.core.utility.extensions

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_SMALL = "dd/MM"
const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
const val ZERO = "0"

fun Date.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun String.parse(pattern: String = DATE_FORMAT_DEFAULT, default: Date = Date()): Date =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this) ?: default

fun String.toCurrency() = this.toBigDecimalOrZero().toCurrency()

fun String.toBigDecimalOrZero(): BigDecimal =
    try { this.toBigDecimalOrNull().orZero() } catch (e: NumberFormatException) { BigDecimal.ZERO }

fun String?.orZero() = if (this.isNullOrBlank()) ZERO else this

fun String.replaceIf(replacement: String, predicate: () -> Boolean) =
    if (predicate()) replacement else this