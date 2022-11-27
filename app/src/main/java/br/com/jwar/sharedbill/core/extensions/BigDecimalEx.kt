package br.com.jwar.sharedbill.core.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

fun BigDecimal?.orZero(): BigDecimal =
    this ?: BigDecimal.ZERO

fun BigDecimal?.toCurrency(): String =
    NumberFormat.getCurrencyInstance().format(
        this?.orZero()?.setScale(2, RoundingMode.CEILING)
    ).orEmpty()