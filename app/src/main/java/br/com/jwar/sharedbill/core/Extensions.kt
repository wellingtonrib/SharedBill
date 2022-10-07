package br.com.jwar.sharedbill.core

import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat

fun Timestamp.format(pattern: String = "dd/MM/yyyy"): String =
    SimpleDateFormat(pattern).format(this.toDate())

fun String.toCurrency() =
    NumberFormat.getCurrencyInstance().format(this.toDouble()).orEmpty()

fun BigDecimal?.orZero(): BigDecimal =
    this ?: BigDecimal.ZERO

fun BigDecimal?.toCurrency(): String =
    NumberFormat.getCurrencyInstance().format(this?.orZero()?.toDouble()).orEmpty()