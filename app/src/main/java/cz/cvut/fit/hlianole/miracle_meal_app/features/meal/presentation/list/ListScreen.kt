package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import cz.cvut.fit.hlianole.miracle_meal_app.features.ErrorState
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    AnalyticsHelper.logEvent("screen viewed", mapOf(
        "screen name" to "list screen")
    )

    ListScreen(
        screenState = screenState,
        onMealClick = { meal ->
            navController.navigate(Screens.MealDetail(meal.id))
        },
        onNavigateBackClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    screenState: ListViewModel.ListScreenState,
    onMealClick: (Meal) -> Unit,
    onNavigateBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    when(screenState) {
                        is ListViewModel.ListScreenState.Loading -> {
                            Text(
                                text = stringResource(R.string.loading),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        is ListViewModel.ListScreenState.Error -> {
                            Text(
                                text = stringResource(R.string.error),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        is ListViewModel.ListScreenState.Loaded -> {
                            Text(
                                text = screenState.name,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (screenState) {
                is ListViewModel.ListScreenState.Loading -> {
                    LoadingState()
                }
                is ListViewModel.ListScreenState.Error -> {
                    ErrorState()
                }
                is ListViewModel.ListScreenState.Loaded -> {
                    Meals(
                        screenState.meals,
                        onMealClick = onMealClick
                    )
                }
            }
        }
    }

}

@Composable
private fun Meals(
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
