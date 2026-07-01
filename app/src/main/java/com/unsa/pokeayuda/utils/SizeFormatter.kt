package com.unsa.pokeayuda.utils

import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

object SizeFormatter {
    fun format(bytes: Long): String {
        if (bytes <= 0L) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (ln(bytes.toDouble()) / ln(1024.0)).toInt()
        return String.format(
            Locale.US,
            "%.2f %s",
            bytes / 1024.0.pow(digitGroups.toDouble()),
            units[digitGroups]
        )
    }
}