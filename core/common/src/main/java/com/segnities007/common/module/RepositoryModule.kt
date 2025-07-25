package com.segnities007.common.module

import com.segnities007.repository.ExpenseRepository
import com.segnities007.repository.ExpenseRepositoryImpl
import com.segnities007.repository.ItemRepository
import com.segnities007.repository.ItemRepositoryImpl
import com.segnities007.repository.StorageRepository
import com.segnities007.repository.StorageRepositoryImpl
import com.segnities007.repository.UserRepository
import com.segnities007.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule =
    module {
        single<UserRepository> { UserRepositoryImpl(get(), get ()) }
        single<ItemRepository> { ItemRepositoryImpl() }
        single<ExpenseRepository> { ExpenseRepositoryImpl() }
        single<StorageRepository> { StorageRepositoryImpl(get(), get()) }
    }
