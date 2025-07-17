package cz.cvut.fit.hlianole.miracle_meal_app.features.search.di

import android.app.Application
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.SearchRepository
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.api.SearchRemoteDataSource
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    factoryOf(::SearchRemoteDataSource)
    singleOf(::SearchRepository)
    viewModelOf(::SearchViewModel)

    single { get<Application>().resources }
}