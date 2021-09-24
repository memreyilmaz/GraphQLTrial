package com.example.graphqltrial.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.graphqltrial.R

fun ImageView.loadImage(imageUrl: String?, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_launcher_foreground)
        .fitCenter()
        .error(R.drawable.ic_launcher_foreground)
        .into(this)
}