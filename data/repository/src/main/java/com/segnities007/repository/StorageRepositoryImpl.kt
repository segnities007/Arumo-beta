package com.segnities007.repository

import com.segnities007.local.dao.StorageDao
import com.segnities007.local.dto.StorageEntity
import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class StorageRepositoryImpl :
    StorageRepository,
    KoinComponent {
    private val storageDao: StorageDao by inject()

    override suspend fun createStorage(storage: Storage) {
        storageDao.create(StorageEntity.fromModel(storage))
    }

    override suspend fun deleteStorage(storage: Storage) {
        storageDao.delete(StorageEntity.fromModel(storage))
    }

    override fun getStorageById(id: String): Flow<Storage?> {
        val result = storageDao.getStorageById(id)
        return result.map { it?.toModel() }
    }

    override fun getStorages(): Flow<List<Storage>> {
        val result = storageDao.getAllStorages()
        return result.map { list -> list.map { it.toModel() } }
    }
}
