package com.segnities007.common.factory

import android.content.Context
import androidx.room.Room
import com.segnities007.local.db.ExpenseDatabase
import com.segnities007.local.db.ItemDatabase
import com.segnities007.local.db.StorageDatabase
import com.segnities007.local.db.UserDatabase
import org.koin.core.scope.Scope

fun Scope.createStorageDatabase(): StorageDatabase {
    val context = get<Context>()
    return Room
        .databaseBuilder(
            context,
            StorageDatabase::class.java,
            "storage.db",
        ).build()
}

fun Scope.createUserDatabase(): UserDatabase {
    val context = get<Context>()
    return Room
        .databaseBuilder(
            context,
            UserDatabase::class.java,
            "user.db",
        ).build()
}

fun Scope.createItemDatabase(): ItemDatabase {
    val context = get<Context>()
    return Room
        .databaseBuilder(
            context,
            ItemDatabase::class.java,
            "item.db",
        ).build()
}

fun Scope.createExpenseDatabase(): ExpenseDatabase {
    val context = get<Context>()
    return Room
        .databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense.db",
        ).build()
}
