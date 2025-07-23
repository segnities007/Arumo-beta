package com.segnities007.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segnities007.local.dto.StorageEntity
import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(storage: StorageEntity)

    @Delete
    suspend fun delete(storage: StorageEntity)

    @Query("SELECT * FROM storages WHERE id = :id")
    fun getStorageById(id: String): Flow<StorageEntity?>

    @Query("SELECT * FROM storages ORDER BY updateAtMillis DESC")
    fun getAllStorages(): Flow<List<StorageEntity>>
}
