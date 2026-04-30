package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val output = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = input.parse(dateString)
        output.format(date ?: return dateString)
    } catch (e: Exception) {
        dateString.take(10)
    }
}