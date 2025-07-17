package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.look

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.hlianole.miracle_meal_app.features.MealCardItemSmall
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LookScreen(
    navController: NavController,
    viewModel: LookViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()
    val query by viewModel.queryStream.collectAsStateWithLifecycle()

    AnalyticsHelper.logEvent("screen viewed", mapOf(
        "screen name" to "look screen")
    )

    SearchScreenContent(
        screenState = screenState,
        query = query,
        onQueryChange = viewModel::search,
        onClearClick = viewModel::clearText,
        onMealClick = { meal ->
            navController.navigate(Screens.MealDetail(meal.id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    screenState: LookViewModel.LookScreenState,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onMealClick: (Meal) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchTopBarContent(
                        query = query,
                        onQueryChange = onQueryChange,
                        onClearClick = onClearClick,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (screenState) {
                is LookViewModel.LookScreenState.Error -> {
                    LookErrorState()
                }
                is LookViewModel.LookScreenState.Loading -> {
                    LoadingState()
                }
                is LookViewModel.LookScreenState.Loaded -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(screenState.meals) { meal ->
                            MealCardItemSmall(
                                meal = meal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = { onMealClick(meal) })
                                    .padding(all = 8.dp)
                                    .border(
                                        width = 4.dp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(24.dp)
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LookErrorState() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.nothing_found),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun SearchTopBarContent(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.search_meals),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = {
                        onClearClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            },
        )
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onSurface
                )
        )
    }
}