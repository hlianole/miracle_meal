package cz.cvut.fit.hlianole.miracle_meal_app.core.presentation.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cz.cvut.fit.hlianole.miracle_meal_app.R

private val fontFamily = FontFamily(
    Font(R.font.archivo_bold, weight = FontWeight.Bold),
    Font(R.font.lexend_deca_light, weight = FontWeight.Light)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        fontFamily = fontFamily,
    ),
)