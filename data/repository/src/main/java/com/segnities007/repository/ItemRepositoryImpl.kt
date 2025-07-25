package com.segnities007.repository

import com.segnities007.local.dao.ItemDao
import com.segnities007.local.dto.ItemEntity
import com.segnities007.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class ItemRepositoryImpl :
    ItemRepository,
    KoinComponent {
    private val itemDao: ItemDao by inject()

    override suspend fun upsertItem(item: Item) {
        itemDao.upsert(ItemEntity.fromModel(item))
    }

    override suspend fun deleteItem(item: Item) {
        itemDao.delete(ItemEntity.fromModel(item))
    }

    override suspend fun getItemById(id: Int): Item? {
        val result = itemDao.getItemById(id)
        return result.map { it?.toModel() }.first()
    }

    override suspend fun getRecentlyItemsFromId(id: Int): List<Item> {
        val result = itemDao.getRecentItemsFromId(id)
        return result.map { list -> list.map { it.toModel() } }.first()
    }

    override suspend fun getItemsByCategoryFromId(
        id: Int,
        category: String
    ): List<Item> {
        val result = itemDao.getItemsByCategoryFromId(category, id)
        return result.map { list -> list.map { it.toModel() } }.first()
    }

    override fun getItems(): Flow<List<Item>> {
        val result = itemDao.getAllItems()
        return result.map { list -> list.map { it.toModel() } }
    }
}
