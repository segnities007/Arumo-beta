package com.segnities007.repository

import com.segnities007.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun upsertUser(user: User)

    suspend fun deleteUser(user: User)
    suspend fun getSavedUserId(): String

    suspend fun getUserById(id: String): User?

    suspend fun getUsers(): List<User>
}
