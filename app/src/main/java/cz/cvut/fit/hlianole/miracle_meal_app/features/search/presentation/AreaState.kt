package cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AreaState(
    data: List<String>,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid (
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data) { elem ->
            CardItem(
                Modifier.fillMaxSize().height(80.dp),
                name = elem,
                imageUrl = null,
                onClick = onClick
            )
        }
    }
}