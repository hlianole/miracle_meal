package cz.cvut.fit.hlianole.miracle_meal_app.core.data.api

import kotlinx.serialization.Serializable

@Serializable
data class MealResponse (
    val meals: List<ApiMeal>,
)