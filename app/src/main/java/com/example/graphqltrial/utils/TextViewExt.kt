package com.example.graphqltrial.utils

import android.widget.TextView

fun TextView.showIfNotNull(text: String?){
    this.apply {
        showIfNot(text.isNullOrEmpty())
        setText(text)
    }
}