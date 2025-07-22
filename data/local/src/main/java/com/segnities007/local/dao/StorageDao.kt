package com.segnities007.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segnities007.model.Storage
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(storage: Storage)

    @Delete
    suspend fun delete(storage: Storage)

    @Query("SELECT * FROM storages WHERE id = :id")
    suspend fun getStorageById(id: String): Flow<Storage?>

    @Query("SELECT * FROM storages ORDER BY updateAt DESC")
    fun getAllStorages(): Flow<List<Storage>>
}
