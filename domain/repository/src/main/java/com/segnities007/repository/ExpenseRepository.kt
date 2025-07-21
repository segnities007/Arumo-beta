package com.segnities007.repository

import com.segnities007.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun upsertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun getExpenseById(id: Int): Flow<Expense?>

    suspend fun getExpenses(): Flow<List<Expense>>
}
