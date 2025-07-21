package com.segnities007.repository

import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun upsertStorage(storage: Storage)

    suspend fun deleteStorage(storage: Storage)

    suspend fun getStorageById(id: String): Flow<Storage?>

    suspend fun getStorages(): Flow<List<Storage>>
}
