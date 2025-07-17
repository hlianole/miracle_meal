package cz.cvut.fit.hlianole.miracle_meal_app.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.detail.DetailScreen
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.home.HomeScreen
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.list.ListScreen
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.look.LookScreen
import cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.saved.SavedScreen
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.TopLevel.Home,
    ) {
        composable<Screens.TopLevel.Home> {
            HomeScreen(navController)
        }

        composable<Screens.TopLevel.Saved> {
            SavedScreen(navController)
        }

        composable<Screens.TopLevel.Search> {
            SearchScreen(navController)
        }

        composable<Screens.TopLevel.Look> {
            LookScreen(navController)
        }

        composable<Screens.MealDetail> {
            DetailScreen(navController)
        }

        composable<Screens.MealList> {
            ListScreen(navController)
        }
    }
}