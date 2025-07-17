package cz.cvut.fit.hlianole.miracle_meal_app.core.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val PrimaryOrange = Color(0xFFE65100)
val SecondaryAmber = Color(0xFFFFA726)
val TertiaryRed = Color(0xFFE53935)

val BackgroundCream = Color(0xFFFFFBF7)
val SurfacePeach = Color(0xFFFFF3E0)

val OnBackground = Color(0xFF444141)
val OnSurface = Color(0xFFE1C5BB)

val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkOnBackground = Color.White
val DarkOnSurface = OnSurface

val LightColorScheme = lightColorScheme(
    primary = PrimaryOrange,
    secondary = SecondaryAmber,
    tertiary = TertiaryRed,
    background = BackgroundCream,
    surface = SurfacePeach,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = OnBackground,
    onSurface = OnSurface,
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryOrange,
    secondary = SecondaryAmber,
    tertiary = TertiaryRed,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
)
