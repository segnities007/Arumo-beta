package com.segnities007.arumo_beta

import android.app.Application
import com.segnities007.common.module.databaseModule
import com.segnities007.common.module.repositoryModule
import com.segnities007.common.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android ContextをKoinに提供
            androidContext(this@MainApplication)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
            )
        }
    }
}
