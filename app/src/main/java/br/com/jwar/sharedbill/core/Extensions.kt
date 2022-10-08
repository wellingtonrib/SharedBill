package br.com.jwar.sharedbill.core

import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"

fun Timestamp.format(pattern: String = DEFAULT_DATE_FORMAT): String =
    this.toDate().format(pattern)

fun Date.format(pattern: String = DEFAULT_DATE_FORMAT): String =
    SimpleDateFormat(pattern).format(this)

fun String.parse(pattern: String = DEFAULT_DATE_FORMAT, default: Date = Date()): Date =
    SimpleDateFormat(pattern).parse(this) ?: default

fun String.toCurrency() =
    NumberFormat.getCurrencyInstance().format(this.toDouble()).orEmpty()

fun BigDecimal?.orZero(): BigDecimal =
    this ?: BigDecimal.ZERO

fun BigDecimal?.toCurrency(): String =
    NumberFormat.getCurrencyInstance().format(this?.orZero()?.toDouble()).orEmpty()

fun Boolean?.orFalse() =
    this ?: false
