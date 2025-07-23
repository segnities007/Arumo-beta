package com.segnities007.repository

import com.segnities007.local.dao.UserDao
import com.segnities007.local.dto.UserEntity
import com.segnities007.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class UserRepositoryImpl :
    UserRepository,
    KoinComponent {
    private val userDao: UserDao by inject()

    override suspend fun upsertUser(user: User) {
        userDao.upsert(UserEntity.fromModel(user))
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(UserEntity.fromModel(user))
    }

    override fun getUser(): Flow<User>{
        val result = userDao.getUser()
        return result
            .map { list -> list.first() }
            .map { it.toModel() }
    }
}
