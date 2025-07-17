package cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.FilterType
import cz.cvut.fit.hlianole.miracle_meal_app.features.ErrorState
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    AnalyticsHelper.logEvent("screen viewed", mapOf(
        "screen name" to "search screen")
    )

    SearchScreen(
        screenState = screenState,
        navController = navController,
        onFilterButtonClick = viewModel::onFilterButtonClick,
        getIngredientImageUrl = viewModel::getIngredientImageUrl
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    screenState: SearchViewModel.SearchScreenState,
    navController: NavController,
    onFilterButtonClick: (FilterType) -> Unit,
    getIngredientImageUrl: (String) -> String,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    FilterBy(
                        screenState = screenState,
                        onFilterButtonClick
                    )
                },
                title = {},
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
        ) {
            when(screenState) {
                is SearchViewModel.SearchScreenState.Loading -> {
                    LoadingState()
                }
                is SearchViewModel.SearchScreenState.Error -> {
                    ErrorState()
                }
                is SearchViewModel.SearchScreenState.CategoryState -> {
                    CategoryState(
                        data = screenState.categories,
                        onClick = { name ->
                            navController.navigate(
                                Screens.MealList(Screens.SearchStates.Category, name)
                            )
                        }
                    )
                }
                is SearchViewModel.SearchScreenState.Area -> {
                    AreaState(
                        data = screenState.ares,
                        onClick = { name ->
                            navController.navigate(
                                Screens.MealList(Screens.SearchStates.Area, name)
                            )
                        }
                    )
                }
                is SearchViewModel.SearchScreenState.FirstLetter -> {
                    FirstLetterState (
                        data = screenState.letters,
                        onClick = { name ->
                            navController.navigate(
                                Screens.MealList(Screens.SearchStates.FirstLetter, name.lowercase())
                            )
                        }
                    )
                }
                is SearchViewModel.SearchScreenState.MainIngredient -> {
                    IngredientState (
                        data = screenState.ingredients,
                        onClick = { name ->
                            navController.navigate(
                                Screens.MealList(Screens.SearchStates.MainIngredient, name)
                            )
                        },
                        getIngredientImageUrl = getIngredientImageUrl
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterBy(
    screenState: SearchViewModel.SearchScreenState,
    onFilterButtonClick: (FilterType) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FilterByButton(
            stringResource(R.string.category),
            selected = screenState is SearchViewModel.SearchScreenState.CategoryState,
            FilterType.Categories,
            onFilterButtonClick
        )
        FilterByButton(
            stringResource(R.string.area),
            selected = screenState is SearchViewModel.SearchScreenState.Area,
            FilterType.Areas,
            onFilterButtonClick
        )
        FilterByButton(
            stringResource(R.string.first_letter),
            selected = screenState is SearchViewModel.SearchScreenState.FirstLetter,
            FilterType.FirstLetter,
            onFilterButtonClick
        )
        FilterByButton(
            stringResource(R.string.main_ingredient),
            selected = screenState is SearchViewModel.SearchScreenState.MainIngredient,
            FilterType.Ingredients,
            onFilterButtonClick
        )
    }
}

@Composable
private fun FilterByButton(
    text: String,
    selected: Boolean,
    filterType: FilterType,
    onClick: (FilterType) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(all = 4.dp)
    ) {
        Button(
            modifier = Modifier
                //.weight(1f)
                .border(
                    width = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.secondary
                ),
            onClick = {
                onClick(filterType)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
        ) {
            Text(
                text = text,
                color = if (selected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
        }
    }
}
