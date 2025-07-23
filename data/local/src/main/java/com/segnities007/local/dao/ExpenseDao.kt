package com.segnities007.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segnities007.local.dto.ExpenseEntity
import com.segnities007.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int): Flow<ExpenseEntity?>

    @Query("SELECT * FROM expenses ORDER BY updateAtMillis DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}
