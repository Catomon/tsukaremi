package com.github.catomon.tsukaremi.di

import com.github.catomon.tsukaremi.data.ReminderManagerImpl
import com.github.catomon.tsukaremi.domain.ReminderManager
import org.koin.dsl.module

val androidModule = module {
    single<ReminderManager> { ReminderManagerImpl(get(), get()) }
}