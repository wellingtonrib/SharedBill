package br.com.jwar.sharedbill.core

import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

const val DATE_FORMAT_SMALL = "dd/MM"
const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
const val DATE_FORMAT_FULL = "dd/MM/yyyy HH:mm"

fun Timestamp.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    this.toDate().format(pattern)

fun Date.format(pattern: String = DATE_FORMAT_DEFAULT): String =
    SimpleDateFormat(pattern).format(this)

fun String.parse(pattern: String = DATE_FORMAT_DEFAULT, default: Date = Date()): Date =
    SimpleDateFormat(pattern).parse(this) ?: default

fun String.toCurrency() =
    this.toBigDecimal().toCurrency()

fun BigDecimal?.orZero(): BigDecimal =
    this ?: BigDecimal.ZERO

fun BigDecimal?.toCurrency(): String =
    NumberFormat.getCurrencyInstance().format(
        this?.orZero()?.setScale(2, RoundingMode.CEILING)
    ).orEmpty()

fun Boolean?.orFalse() =
    this ?: false
