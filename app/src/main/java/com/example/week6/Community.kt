package com.example.week6

data class Community(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val memberCount: Int,
    val isJoined: Boolean = false
)
