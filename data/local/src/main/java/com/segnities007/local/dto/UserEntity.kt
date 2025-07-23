package com.segnities007.local.dto

import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.segnities007.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val iconUri: String = "",
    val isPrime: Boolean = false,
) {
    fun toModel(): User =
        User(
            id = id,
            name = name,
            email = email,
            password = password,
            iconUri = if (iconUri.isNotEmpty()) iconUri.toUri() else null,
            isPrime = isPrime,
        )

    companion object {
        fun fromModel(user: User): UserEntity =
            UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                password = user.password,
                iconUri = user.iconUri?.toString() ?: "",
                isPrime = user.isPrime,
            )
    }
}
