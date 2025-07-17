package cz.cvut.fit.hlianole.miracle_meal_app

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cz.cvut.fit.hlianole.miracle_meal_app.core.presentation.ui.theme.MiracleMealTheme
import cz.cvut.fit.hlianole.miracle_meal_app.features.notifications.ReminderWorker
import androidx.core.content.edit

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        { granted ->
            if (granted) {
                Log.d("NotificationPerm", "Permission granted")
            } else {
                Log.d("NotificationPerm", "Permission NOT granted")
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        // Track last time user opened the app
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit() {
            putLong("last_used_time", System.currentTimeMillis())
        }

        // Schedule reminder for 3 days later
        ReminderWorker.schedule(this)

        setContent {
            MiracleMealTheme {
                MainScreen()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}