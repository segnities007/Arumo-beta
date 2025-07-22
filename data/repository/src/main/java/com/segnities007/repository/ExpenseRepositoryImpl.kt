package com.segnities007.repository

import com.segnities007.local.dao.ExpenseDao
import com.segnities007.model.Expense
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExpenseRepositoryImpl :
    ExpenseRepository,
    KoinComponent {
    private val expenseDao: ExpenseDao by inject()

    override suspend fun upsertExpense(expense: Expense) {
        expenseDao.upsert(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.delete(expense)
    }

    override suspend fun getExpenseById(id: Int): Flow<Expense?> = expenseDao.getExpenseById(id)

    override suspend fun getExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
}
