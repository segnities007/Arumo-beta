package com.segnities007.common.module

import com.segnities007.login.LoginViewModel
import com.segnities007.storage.StorageViewModel
import com.segnities007.navigation.NavigationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { LoginViewModel() }
        viewModel { StorageViewModel() }
        viewModel { NavigationViewModel(get(), get()) }
    }
