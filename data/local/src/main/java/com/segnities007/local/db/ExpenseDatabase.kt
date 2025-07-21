package com.segnities007.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segnities007.local.Converters
import com.segnities007.local.dao.ExpenseDao
import com.segnities007.model.Expense

private const val version = 1

@Database(entities = [Expense::class], version = version)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
