package com.segnities007.repository

import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun createStorage(storage: Storage)

    suspend fun deleteStorage(storage: Storage)

    fun getStorageById(id: String): Flow<Storage?>

    fun getStorages(): Flow<List<Storage>>
}
