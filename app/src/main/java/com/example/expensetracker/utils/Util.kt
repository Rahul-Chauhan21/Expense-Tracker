package com.example.expensetracker.utils

import java.text.DecimalFormat

fun formatAmount(amount: Double): String {
    val format = DecimalFormat("+$#,##0.00;-$#,##0.00")
    return format.format(amount)
}

