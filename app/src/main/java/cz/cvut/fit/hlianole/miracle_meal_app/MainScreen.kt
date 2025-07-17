package cz.cvut.fit.hlianole.miracle_meal_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.cvut.fit.hlianole.miracle_meal_app.core.presentation.ui.Navigation

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        val navController = rememberNavController()

        Navigation(
            navController = navController,
            modifier = Modifier.weight(1f),
        )

        val currentEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentEntry?.destination
        val shouldShowBottomNavigation = currentDestination?.let(::hasBottomNavigation) ?: false
        if (shouldShowBottomNavigation) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    ),
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Screens.TopLevel.all.forEach { screen ->
                        NavBarItem(
                            imageVector = screen.icon,
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(screen::class)
                            } == true,
                            onClick = {
                                navController.navigate(screen) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun hasBottomNavigation(route: NavDestination): Boolean {
    return Screens.TopLevel.all.any { screen ->
        route.hasRoute(screen::class)
    }
}

@Composable
private fun RowScope.NavBarItem (
    imageVector: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = contentColor,
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    )
}