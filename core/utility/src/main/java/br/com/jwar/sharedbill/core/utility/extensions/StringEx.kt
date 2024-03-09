package br.com.jwar.sharedbill.core.utility.extensions

import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
const val ZERO = "0"

fun Date.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Date.defaultFormat(format: Int = DateFormat.DEFAULT): String =
    DateFormat.getDateInstance(format, Locale.getDefault()).format(this)

fun String.toCurrency() = this.toBigDecimalOrZero().toCurrency()

fun String.toBigDecimalOrZero(): BigDecimal =
    try { this.toBigDecimalOrNull().orZero() } catch (e: NumberFormatException) { BigDecimal.ZERO }

fun String?.orZero() = if (this.isNullOrBlank()) ZERO else this
