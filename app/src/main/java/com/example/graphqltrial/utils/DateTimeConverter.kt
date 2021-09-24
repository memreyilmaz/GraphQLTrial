package com.example.graphqltrial.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

interface DateTimeConverter {

    @SuppressLint("SimpleDateFormat")
    fun formatDate(
        date: String?,
        currentDateFormat: String = API_DATE_FORMAT,
        targetFormat: String = RESPONSE_DATE_FORMAT,
    ): String? {
        if (date.isNullOrEmpty()) return null

        val defaultFormat = SimpleDateFormat(currentDateFormat)

        try {
            val formatDate = defaultFormat.parse(date)
            formatDate?.let {
                val desiredFormat = SimpleDateFormat(targetFormat)
                return desiredFormat.format(formatDate)
            }
        } catch (ex: ParseException) {
            return null
        }

        return null
    }

    companion object {
        const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val RESPONSE_DATE_FORMAT = "yyyy-MM-dd"
    }
}