package com.github.catomon.tsukaremi.data.local.platform

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.github.catomon.tsukaremi.data.local.AppDatabase

actual fun createDatabase(): AppDatabase {
    return Room.databaseBuilder<AppDatabase>(
        userFolderPath + "reminders.db"
    ).setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(true)
        .build()
}