package cz.cvut.fit.hlianole.miracle_meal_app.core.data.api

import kotlinx.serialization.Serializable

@Serializable
data class MealShortResponse(
    val meals: List<MealId>
)

@Serializable
data class MealId (
    val idMeal: Long
)
