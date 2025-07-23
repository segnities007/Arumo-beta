package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.segnities007.local.dao.ExpenseDao
import com.segnities007.local.dto.ExpenseEntity

private const val version = 1

@Database(entities = [ExpenseEntity::class], version = version)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
