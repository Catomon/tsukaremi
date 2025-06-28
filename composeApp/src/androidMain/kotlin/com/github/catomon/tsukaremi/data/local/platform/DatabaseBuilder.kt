package com.github.catomon.tsukaremi.data.local.platform

import androidx.room.Room
import com.github.catomon.tsukaremi.data.local.AppDatabase
import com.github.catomon.tsukaremi.appContext

actual fun createDatabase(): AppDatabase {
    val room = Room.databaseBuilder<AppDatabase>(
        appContext!!,
        "reminders.db"
    )
        .fallbackToDestructiveMigration(false)
        .build()
    return room
}