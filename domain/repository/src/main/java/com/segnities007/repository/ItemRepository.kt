package com.segnities007.repository

import com.segnities007.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun upsertItem(item: Item)

    suspend fun deleteItem(item: Item)

    fun getItemById(id: Int): Flow<Item?>

    fun getItems(): Flow<List<Item>>
}
