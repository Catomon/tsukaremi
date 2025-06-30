package com.github.catomon.tsukaremi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.util.logMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderReceiver : BroadcastReceiver(), KoinComponent {
    override fun onReceive(context: Context, intent: Intent) {
        val repo: RemindersRepository by inject()
        val remService: ReminderService by inject()

        CoroutineScope(Dispatchers.IO).launch {
            val reminderId =
                intent.hasExtra("reminderId").let { if (it == true) intent.getIntExtra("reminderId", -1) else -1 }

            if (reminderId == -1) return@launch
            val reminder = repo.getReminderById(reminderId) ?: return@launch

            remService.showNotification(reminder)
        }
    }
}