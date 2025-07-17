package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.di

import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.detail.DetailViewModel
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.home.HomeViewModel
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.list.ListViewModel
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.look.LookViewModel
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.saved.SavedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mealMode = module {
    viewModelOf(::DetailViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ListViewModel)
    viewModelOf(::LookViewModel)
    viewModelOf(::SavedViewModel)
}