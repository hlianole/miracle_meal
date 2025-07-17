package cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.domain.Category

@Composable
fun CategoryState(
    data: List<Category>,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid (
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((-8).dp),
        horizontalArrangement = Arrangement.spacedBy((-8).dp)
    ) {
        items(data) { elem ->
            CardItem(
                Modifier.aspectRatio(1f).padding(8.dp).clickable {
                    onClick(elem.name)
                },
                name = elem.name,
                imageUrl = elem.imageUrl,
                onClick = onClick
            )
        }
    }
}