package com.segnities007.repository

import com.segnities007.local.dao.ExpenseDao
import com.segnities007.local.dto.ExpenseEntity
import com.segnities007.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExpenseRepositoryImpl :
    ExpenseRepository,
    KoinComponent {
    private val expenseDao: ExpenseDao by inject()

    override suspend fun upsertExpense(expense: Expense) {
        expenseDao.upsert(ExpenseEntity.fromModel(expense))
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.delete(ExpenseEntity.fromModel(expense))
    }

    override fun getExpenseById(id: Int): Flow<Expense?> {
        val result = expenseDao.getExpenseById(id)
        return result.map { it?.toModel() }
    }

    override fun getExpenses(): Flow<List<Expense>> {
        val result = expenseDao.getAllExpenses()
        return result.map { list -> list.map { it.toModel() } }
    }
}
