package com.segnities007.model

import android.net.Uri

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val iconUri: Uri? = null,
    val isPrime: Boolean = false,
)
