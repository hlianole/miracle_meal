package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.saved

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.hlianole.miracle_meal_app.features.MealCardItem
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    AnalyticsHelper.logEvent("screen viewed", mapOf(
        "screen name" to "saved screen")
    )

    SavedScreen(
        screenState = screenState,
        onMealClick = { meal ->
            navController.navigate(Screens.MealDetail(meal.id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SavedScreen(
    screenState: SavedViewModel.SavedScreenState,
    onMealClick: (Meal) -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Saved meals",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (screenState) {
                is SavedViewModel.SavedScreenState.Loading -> {
                    LoadingState()
                }
                is SavedViewModel.SavedScreenState.NoSaved -> {
                    NoSavedState()
                }
                is SavedViewModel.SavedScreenState.Loaded -> {
                    LoadedState(
                        meals = screenState.meals,
                        onMealClick = onMealClick
                    )
                }
            }
        }
    }
}

@Composable
private fun NoSavedState() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.no_meal_saved),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun LoadedState(
    meals: List<Meal>,
    onMealClick: (Meal) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(meals, key = {it.id}) { meal ->
            MealCard(meal) {
                onMealClick(meal)
            }
        }
    }
}

@Composable
private fun MealCard(
    meal: Meal,
    onMealClick: (Meal) -> Unit,
) {
    Card(
        modifier = Modifier
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            onMealClick(meal)
        },
    ) {
        MealCardItem(
            meal = meal,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}