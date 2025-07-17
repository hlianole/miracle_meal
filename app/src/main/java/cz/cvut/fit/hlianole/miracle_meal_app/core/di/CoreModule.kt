package cz.cvut.fit.hlianole.miracle_meal_app.core.di

import android.app.Application
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.api.ApiClient
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.api.MealRemoteDataSource
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.db.MealDatabase
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.db.MealLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    single {
        MealDatabase.newInstance(androidContext())
    }

    singleOf(::ApiClient)

    factoryOf(::MealRemoteDataSource)
    single {
        get<MealDatabase>().mealDao()
    }
    factoryOf(::MealLocalDataSource)

    singleOf(::MealRepository)

    single {
        get<Application>().resources
    }
}