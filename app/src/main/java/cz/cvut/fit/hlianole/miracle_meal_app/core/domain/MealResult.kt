package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

data class MealResult (
    val meals: List<Meal>,
    val success: Boolean,
)
