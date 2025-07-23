package com.segnities007.repository

import com.segnities007.local.dao.ItemDao
import com.segnities007.local.dto.ItemEntity
import com.segnities007.model.Item
import kotlinx.coroutines.flow.Flow
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

    override fun getItemById(id: Int): Flow<Item?>{
        val result = itemDao.getItemById(id)
        return result.map { it?.toModel() }
    }

    override fun getItems(): Flow<List<Item>>{
        val result = itemDao.getAllItems()
        return result.map { list -> list.map { it.toModel() } }
    }
}
