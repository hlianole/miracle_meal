package cz.cvut.fit.hlianole.miracle_meal_app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// object Look was made later than Search,
// it was supposed to be named "Search", but that name
// was already taken
sealed interface Screens {
    sealed interface TopLevel: Screens {
        val icon: ImageVector

        @Serializable
        data object Home: TopLevel {
            override val icon: ImageVector
                get() = Icons.Default.Home
        }

        @Serializable
        data object Search: TopLevel {
            override val icon: ImageVector
                get() = Icons.Rounded.Star
        }

        @Serializable
        data object Look: TopLevel {
            override val icon: ImageVector
                get() = Icons.Default.Search
        }

        @Serializable
        data object Saved: TopLevel {
            override val icon: ImageVector
                get() = Icons.Default.Favorite
        }

        companion object {
            val all
                get() = listOf(Home, Search, Look, Saved)
        }
    }

    @Serializable
    data class MealList(val state: SearchStates, val name: String): Screens

    @Serializable
    data class MealDetail(val id: Long): Screens

    enum class SearchStates {
        Category, Area, FirstLetter, MainIngredient
    }
}