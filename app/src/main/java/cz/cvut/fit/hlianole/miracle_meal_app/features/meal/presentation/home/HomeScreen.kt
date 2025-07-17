package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.hlianole.miracle_meal_app.features.MealCardItem
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import cz.cvut.fit.hlianole.miracle_meal_app.features.ErrorState
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    AnalyticsHelper.logEvent("screen viewed", mapOf(
        "screen name" to "home screen")
    )

    HomeScreen(
        screenState = screenState,
        onMealClick = { meal ->
            navController.navigate(Screens.MealDetail(meal.id))
        },
        onGenerateNew = viewModel::generateNew
    )
}

@Composable
private fun HomeScreen(
    screenState: HomeViewModel.HomeScreenState,
    onMealClick: (Meal) -> Unit,
    onGenerateNew: () -> Unit
) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 32.dp),
                    text = "Random meal!",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            when(screenState) {
                is HomeViewModel.HomeScreenState.Loading -> {
                    LoadingState()
                }
                is HomeViewModel.HomeScreenState.Loaded -> {
                    LoadedState(
                        meal = screenState.mealResult.meals.first(),
                        onMealClick = onMealClick,
                        onGenerateNew = onGenerateNew
                    )
                }
                is HomeViewModel.HomeScreenState.Error -> {
                    ErrorState()
                }
            }
        }
    }
}

@Composable
private fun LoadedState(
    meal: Meal,
    onMealClick: (Meal) -> Unit,
    onGenerateNew: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                modifier = Modifier,
            )
        }
        GenerateMeal (
            onClick = onGenerateNew
        )
    }
}

@Composable
private fun GenerateMeal(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier,
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp),
            text = "Get new meal",
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
