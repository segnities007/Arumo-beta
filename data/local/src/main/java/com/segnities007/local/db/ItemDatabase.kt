package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segnities007.local.Converters
import com.segnities007.local.dao.ItemDao
import com.segnities007.model.Item

private const val version = 1

@Database(entities = [Item::class], version = version)
@TypeConverters(Converters::class)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
