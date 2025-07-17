package cz.cvut.fit.hlianole.miracle_meal_app

import android.app.Application
import cz.cvut.fit.hlianole.miracle_meal_app.core.di.coreModule
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.di.mealMode
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AnalyticsHelper.init()
        startKoin {
            androidContext(this@App)
            modules(
                coreModule, searchModule, mealMode
            )
        }
    }
}