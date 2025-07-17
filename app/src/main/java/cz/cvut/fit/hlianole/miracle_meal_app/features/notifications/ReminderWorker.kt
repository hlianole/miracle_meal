package cz.cvut.fit.hlianole.miracle_meal_app.features.notifications

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import androidx.core.content.edit

// Checks if user hasn't opened the app in 3 days, then shows a notification.
class ReminderWorker(
    context: Context,
    parameters: WorkerParameters
): CoroutineWorker(context, parameters) {

    // SharedPrefs to store and retrieve the last used timestamp
    private val sharedPreferences = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )
    private val notificationHelper = NotificationHelper(context)

    // If user hasn't used app for more than 3 days — shows reminder
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val lastUsedTime = sharedPreferences.getLong("last_used_time", 0)

        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L
        val threeDays = oneDay * 3

        if (now - lastUsedTime > threeDays) {
            notificationHelper.showReminderNotification()
            return Result.success()
        }

        scheduleNextCheck()

        return Result.success()
    }

    // Schedules the next check for reminder in 24 hours
    private fun scheduleNextCheck() {
        val workManager = WorkManager.getInstance(applicationContext)

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()

        workManager.enqueue(workRequest)
    }

    companion object {
        // Schedules the first check 72 hours after calling this method
        // Also updates the "last used" timestamp in SharedPrefs
        fun schedule(context: Context) {
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit() {
                putLong("last_used_time", System.currentTimeMillis())
            }

            val workManager = WorkManager.getInstance(context)

            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                //.setInitialDelay(15, TimeUnit.SECONDS)
                .setInitialDelay(72, TimeUnit.HOURS)
                .build()

            workManager.enqueueUniqueWork(
                uniqueWorkName = "reminder_work",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}