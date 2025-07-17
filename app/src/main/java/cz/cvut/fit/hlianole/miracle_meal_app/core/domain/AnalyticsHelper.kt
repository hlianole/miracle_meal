package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

object AnalyticsHelper {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun init() {
        firebaseAnalytics = Firebase.analytics
    }

    fun logEvent(eventName: String, params: Map<String, Any> = emptyMap()) {
        val bundle = Bundle()
        params.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Float -> bundle.putDouble(key, value.toDouble())
                is Boolean -> bundle.putBoolean(key, value)
                else -> bundle.putString(key, value.toString())
            }
        }
        Firebase.analytics.logEvent(eventName, bundle)
    }
}