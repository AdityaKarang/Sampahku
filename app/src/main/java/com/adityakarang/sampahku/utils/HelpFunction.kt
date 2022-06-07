package com.adityakarang.sampahku.utils

import java.text.DecimalFormat



object HelpFunction {

    fun rpFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price.toLong())
    }
}