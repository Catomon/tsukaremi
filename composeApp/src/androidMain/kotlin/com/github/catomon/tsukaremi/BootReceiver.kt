package com.github.catomon.tsukaremi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootReceiver : BroadcastReceiver(), KoinComponent {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val repo: RemindersRepository by inject()
            val reminderService: ReminderService by inject()

            CoroutineScope(Dispatchers.IO).launch {
                val reminders = repo.getAllRemindersList()
                reminders.forEach { reminder ->
                    reminderService.scheduleReminder(reminder)
                }
            }
        }
    }
}