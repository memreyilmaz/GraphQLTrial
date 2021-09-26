package com.example.graphqltrial.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
 * Data class for using various responses in common
 */
@Parcelize
data class User(
    val avatar: String,
    val name: String?,
    val nickname: String?,
    val bio: String?,
    val followers: String,
    val following: String,
    val email: String?,
    val website: String?,
    val twitterUser: String?,
    val repositories: List<Repository>
) : Parcelable