package com.example.graphqltrial.data.model

data class Repository(
    val name: String,
    val description: String?,
    val url: String,
    val stargazerCount: String,
    val creationDate: String
)