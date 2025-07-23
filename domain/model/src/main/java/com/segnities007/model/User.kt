package com.segnities007.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val iconUrl: String = "",
    val isPrime: Boolean = false,
)
