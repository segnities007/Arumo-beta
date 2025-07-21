package com.segnities007.common.module

import com.segnities007.common.factory.createExpenseDatabase
import com.segnities007.common.factory.createItemDatabase
import com.segnities007.common.factory.createStorageDatabase
import com.segnities007.common.factory.createUserDatabase
import com.segnities007.local.db.ExpenseDatabase
import com.segnities007.local.db.ItemDatabase
import com.segnities007.local.db.StorageDatabase
import com.segnities007.local.db.UserDatabase
import org.koin.dsl.module

val databaseModule =
    module {
        single { createUserDatabase() }
        single { createItemDatabase() }
        single { createExpenseDatabase() }
        single { createStorageDatabase() }
        single { get<UserDatabase>().userDao() }
        single { get<ItemDatabase>().itemDao() }
        single { get<ExpenseDatabase>().expenseDao() }
        single { get<StorageDatabase>().storageDao() }
    }
