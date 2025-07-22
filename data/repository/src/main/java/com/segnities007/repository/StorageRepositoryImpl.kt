package com.segnities007.repository

import com.segnities007.local.dao.StorageDao
import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class StorageRepositoryImpl :
    StorageRepository,
    KoinComponent {
    private val storageDao: StorageDao by inject()

    override suspend fun upsertStorage(storage: Storage) {
        storageDao.upsert(storage)
    }

    override suspend fun deleteStorage(storage: Storage) {
        storageDao.delete(storage)
    }

    override suspend fun getStorageById(id: String): Flow<Storage?> = storageDao.getStorageById(id)

    override suspend fun getStorages(): Flow<List<Storage>> = storageDao.getAllStorages()
}
