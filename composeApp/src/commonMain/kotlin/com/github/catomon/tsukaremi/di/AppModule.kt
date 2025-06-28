package com.github.catomon.tsukaremi.di

import androidx.lifecycle.SavedStateHandle
import com.github.catomon.tsukaremi.data.local.platform.createDatabase
import com.github.catomon.tsukaremi.data.repository.RemindersRepositoryImpl
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.ui.viewmodel.EditViewModel
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<RemindersRepository> {
        RemindersRepositoryImpl(createDatabase().reminderDao())
    }

    viewModel { MainViewModel(get(), get()) }

    viewModel { EditViewModel(get(), get()) }
}