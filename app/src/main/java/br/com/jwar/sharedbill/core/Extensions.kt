package br.com.jwar.sharedbill.core

import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat

fun Timestamp.format(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern).format(this.toDate())
}

fun String.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this.toDouble()).orEmpty()
}