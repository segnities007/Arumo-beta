package com.segnities007.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segnities007.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): Flow<Expense?>

    @Query("SELECT * FROM expenses ORDER BY updateAt DESC")
    fun getAllExpenses(): Flow<List<Expense>>
}
