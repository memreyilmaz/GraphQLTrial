package com.example.graphqltrial.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    val name: String,
    val description: String?,
    val url: String,
    val stargazerCount: String,
    val creationDate: String
) : Parcelable