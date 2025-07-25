package com.segnities007.repository

import com.segnities007.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun upsertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun getItemById(id: Int): Item?
    suspend fun getRecentlyItemsFromId(id: Int): List<Item>
    suspend fun getItemsByCategoryFromId(id: Int, category: String): List<Item>

    fun getItems(): Flow<List<Item>>
}
