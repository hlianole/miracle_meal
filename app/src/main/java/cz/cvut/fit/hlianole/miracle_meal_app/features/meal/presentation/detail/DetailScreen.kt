package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.features.ErrorState
import cz.cvut.fit.hlianole.miracle_meal_app.features.LoadingState

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = koinViewModel()
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    DetailScreen(
        screenState = screenState,
        onSaveClick = viewModel::onSaveClick,
        onNavigateBackClick = {
            navController.popBackStack()
        },
        onCategoryClick = { category ->
            navController.navigate(
                Screens.MealList(Screens.SearchStates.Category, category)
            )
        },
        onAreaClick = { area ->
            navController.navigate(
                Screens.MealList(Screens.SearchStates.Area, area)
            )
        }
    )
}

@Composable
private fun DetailScreen(
    screenState: DetailViewModel.DetailScreenState,
    onSaveClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onAreaClick: (String) -> Unit
) {
    when (screenState) {
        is DetailViewModel.DetailScreenState.Loaded -> {
            screenState.meal?.let { meal ->
                Scaffold(
                    topBar = {
                        DetailTopBar(
                            saved = screenState.saved,
                            onSaveClick,
                            onNavigateBackClick,
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        MealDetail(
                            meal = meal,
                            onCategoryClick = onCategoryClick,
                            onAreaClick = onAreaClick
                        )
                    }
                }
            }
        }
        is DetailViewModel.DetailScreenState.Loading -> {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = onNavigateBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
                LoadingState()
            }
        }
        is DetailViewModel.DetailScreenState.Error -> {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = onNavigateBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
                ErrorState()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    saved: Boolean,
    onSaveClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onSaveClick) {
                Icon(
                    imageVector = if (saved) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .height(60.dp)
    )
}

@Composable
private fun MealDetail(
    meal: Meal,
    onCategoryClick: (String) -> Unit,
    onAreaClick: (String) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                MealHeader(meal)
                MealDetails(
                    meal = meal,
                    onCategoryClick = onCategoryClick,
                    onAreaClick = onAreaClick
                )
            }
        }
    }
}

@Composable
private fun MealHeader(
    meal: Meal,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        AsyncImage(
            model = meal.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = meal.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun MealDetails(
    meal: Meal,
    onCategoryClick: (String) -> Unit,
    onAreaClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MealCategoryAndArea(
            meal = meal,
            onCategoryClick = onCategoryClick,
            onAreaClick = onAreaClick
        )
        MealTags(meal)
        MealYoutubeLink(meal) { url ->
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
        MealIngredients(meal)
        MealInstructions(meal)
        MealSource(meal) { url ->
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }
}

@Composable
private fun MealCategoryAndArea(
    meal: Meal,
    onCategoryClick: (String) -> Unit,
    onAreaClick: (String) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.category),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onCategoryClick(meal.category)
                    },
                text = meal.category,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.area),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onAreaClick(meal.area)
                    },
                text = meal.area,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
private fun MealTags(
    meal: Meal,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.tags),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.size(8.dp))
        if (meal.tags.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(meal.tags) { tag ->
                    Text(
                        text = tag,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_tags),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun MealYoutubeLink(
    meal: Meal,
    onLinkClick: (String) -> Unit
) {
    if (!meal.youtube.isNullOrEmpty()) {
        Button(
            onClick = {
                onLinkClick(meal.youtube)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.youtube_guide),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    } else {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(0.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_youtube_guide_available),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun MealIngredients(
    meal: Meal,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.ingredients),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            meal.ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(60.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary
                            )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = ingredient.ingredient!!,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = ingredient.measure!!,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MealInstructions(
    meal: Meal,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.instructions),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = meal.instructions,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MealSource(
    meal: Meal,
    onLinkClick: (String) -> Unit
) {
    if (!meal.source.isNullOrEmpty()) {
        Button(
            onClick = {
                onLinkClick(meal.source)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.source_link),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    } else {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(0.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_source_link_available),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}