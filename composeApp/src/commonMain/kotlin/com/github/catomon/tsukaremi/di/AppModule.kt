package com.github.catomon.tsukaremi.di

import com.github.catomon.tsukaremi.data.local.platform.createDatabase
import com.github.catomon.tsukaremi.data.repository.RemindersRepositoryImpl
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single { MainViewModel(get()) }

    single<RemindersRepository> {
        RemindersRepositoryImpl(createDatabase().reminderDao())
    }
}