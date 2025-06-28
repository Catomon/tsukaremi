package com.github.catomon.tsukaremi

import android.app.Application
import android.content.Context
import com.github.catomon.tsukaremi.di.androidModule
import com.github.catomon.tsukaremi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

var appContext: Context? = null

class TsukaremiApp : Application() {
    override fun onCreate() {
        super.onCreate()

        appContext = this

        startKoin {
            androidLogger()
            androidContext(this@TsukaremiApp)
            modules(appModule, androidModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        appContext = null
    }
}