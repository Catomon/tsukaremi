package com.github.catomon.tsukaremi.di

import com.github.catomon.tsukaremi.data.ReminderServiceImpl
import com.github.catomon.tsukaremi.domain.ReminderService
import org.koin.dsl.module

val androidModule = module {
    single<ReminderService> { ReminderServiceImpl(get(), get()) }
}