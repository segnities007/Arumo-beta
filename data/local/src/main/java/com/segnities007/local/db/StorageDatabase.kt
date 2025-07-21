package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segnities007.local.Converters
import com.segnities007.local.dao.StorageDao
import com.segnities007.model.Storage

private const val version = 1

@Database(entities = [Storage::class], version = version)
@TypeConverters(Converters::class)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun storageDao(): StorageDao
}
