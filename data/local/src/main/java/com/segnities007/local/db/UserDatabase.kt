package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segnities007.local.dao.UserDao
import com.segnities007.local.dto.UserEntity
import com.segnities007.model.User

private const val version = 1

@Database(entities = [UserEntity::class], version = version)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
