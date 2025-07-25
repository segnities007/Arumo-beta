package com.segnities007.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.segnities007.local.dao.StorageDao
import com.segnities007.local.dto.StorageEntity
import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

val Context.dataStorageIdStore: DataStore<Preferences> by preferencesDataStore(name = "storage_id")


class StorageRepositoryImpl(
    private val context: Context,
    private val storageDao: StorageDao
):
    StorageRepository,
    KoinComponent {

        override suspend fun getSavedStorageId(): String {
            val storageIndex = stringPreferencesKey("storage_id")
            val storageId: Flow<String> = context.dataStorageIdStore.data
                .map { preferences ->
                    preferences[storageIndex] ?: ""
                }
            return storageId.first()
        }

    override suspend fun createStorage(storage: Storage) {
        storageDao.create(StorageEntity.fromModel(storage))
    }

    override suspend fun deleteStorage(storage: Storage) {
        storageDao.delete(StorageEntity.fromModel(storage))
    }

    override suspend fun getStorageById(id: String): Storage? {
        val result = storageDao.getStorageById(id)
        return result.map { it?.toModel() }.first()
    }

    override suspend fun getStorages(): List<Storage> {
        val result = storageDao.getAllStorages()
        return result.map { list -> list.map { it.toModel() } }.first()
    }

    override suspend fun saveStorage(storage: Storage) {
        val storageIndex = stringPreferencesKey("storage_id")
        context.dataStorageIdStore.edit { preferences ->
            preferences[storageIndex] = storage.id
        }
    }
}
