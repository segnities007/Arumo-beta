package com.segnities007.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segnities007.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Flow<Item?>

    @Query("SELECT * FROM items ORDER BY updateAt DESC")
    fun getAllItems(): Flow<List<Item>>
}
