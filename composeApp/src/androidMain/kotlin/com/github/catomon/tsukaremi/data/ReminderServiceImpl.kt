package com.github.catomon.tsukaremi.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.catomon.tsukaremi.ReminderReceiver
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

actual class ReminderServiceImpl(private val context: Context, private val repository: RemindersRepository) :
    ReminderService {
    override suspend fun scheduleReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.canScheduleExactAlarms()
            } else true

            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("reminderId", reminder.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                reminder.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val triggerAtMillis = reminder.remindAt.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()

            if (canScheduleExactAlarms)
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            else
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
        }
    }

    override suspend fun cancelReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                reminder.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)

            repository.updateReminder(reminder.copy(isCompleted = true))
        }
    }

    override suspend fun showNotification(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            val channelId = "reminder_channel"
            val notificationId = System.currentTimeMillis().toInt()

            val channel = NotificationChannel(
                channelId,
                "Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(reminder.title)
                .setContentText(reminder.description)
                .setAutoCancel(true)
                .build()

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, notification)

            repository.updateReminder(reminder.copy(isCompleted = true))
        }
    }

    override suspend fun restartReminder(reminder: Reminder) {
        cancelReminder(reminder)
        repository.updateReminder(
            reminder.copy(
                isCompleted = false,
                remindAt = run {
                    val instant = Instant.ofEpochMilli(System.currentTimeMillis() + reminder.remindIn)
                    val zoneId = ZoneOffset.UTC
                    instant.atZone(zoneId).toLocalDateTime()
                }
            ))
        scheduleReminder(reminder)
    }
}