package com.segnities007.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.segnities007.local.dao.UserDao
import com.segnities007.local.dto.UserEntity
import com.segnities007.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue


val Context.dataUserIdStore: DataStore<Preferences> by preferencesDataStore(name = "user_id")
class UserRepositoryImpl(
    private val context: Context,
    private val userDao: UserDao,
) :
    UserRepository,
    KoinComponent {

        override suspend fun getSavedUserId(): String {
            val userIndex = stringPreferencesKey("user_id")
            val userId: Flow<String> = context.dataUserIdStore.data
                .map { preferences ->
                    preferences[userIndex] ?: ""
                }
            return userId.first()
        }

    override suspend fun upsertUser(user: User) {
        userDao.upsert(UserEntity.fromModel(user))
        val userIndex = stringPreferencesKey("user_id")
        context.dataUserIdStore.edit { preferences ->
            preferences[userIndex] = user.id
        }
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(UserEntity.fromModel(user))
    }

    override suspend fun getUserById(id: String): User? {
        val user = userDao.getUserById(id)
        return user.map { it?.toModel() }.first()
    }

    override suspend fun getUsers(): List<User> {
        val users = userDao.getUsers()
        return users.map { list -> list.map { it.toModel() } }.first()
    }
}
