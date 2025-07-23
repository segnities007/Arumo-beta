package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segnities007.local.dao.ItemDao
import com.segnities007.local.dto.ItemEntity
import com.segnities007.model.Item

private const val version = 1

@Database(entities = [ItemEntity::class], version = version)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
