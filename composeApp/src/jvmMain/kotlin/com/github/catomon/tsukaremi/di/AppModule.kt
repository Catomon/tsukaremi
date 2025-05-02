package com.github.catomon.tsukaremi.di

import com.github.catomon.tsukaremi.ui.viewmodels.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single { MainViewModel() }
}