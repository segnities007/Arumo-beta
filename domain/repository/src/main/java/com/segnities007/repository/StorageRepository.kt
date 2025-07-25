package com.segnities007.repository

import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun createStorage(storage: Storage)

    suspend fun saveStorage(storage: Storage)

    suspend fun deleteStorage(storage: Storage)
    suspend fun getSavedStorageId(): String
    suspend fun getStorageById(id: String): Storage?

    suspend fun getStorages(): List<Storage>
}
