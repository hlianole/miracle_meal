package cz.cvut.fit.hlianole.miracle_meal_app.features.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fit.hlianole.miracle_meal_app.R

// Centralized class to manage all types of notifications.
class NotificationHelper(
    private val context: Context
) {
    // Unique ID for the notification channel
    private val channelId = "reminder_channel"

    // User-visible name for the notification channel
    private val channelName = "Meal Reminder"

    init {
        createChannel()
    }

    // Creates a high-importance notification channel
    // required for showing notifications on Android 8+
    private fun createChannel() {
        val serviceChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder"
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    // Displays a reminder notification with high priority
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showReminderNotification() {
        // Creates an intent to open the app when notification is clicked
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Wraps the intent in a PendingIntent so it can be triggered by the notification system
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Builds the notification with title, text, icon and actions
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_reminder)
            .setContentTitle("Meals are waiting for you!")
            .setContentText("Visit Miracle Meal App — we have something for you")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Displays the notification
        val manager = NotificationManagerCompat.from(context)
        manager.notify(1, notification.build())
    }
}