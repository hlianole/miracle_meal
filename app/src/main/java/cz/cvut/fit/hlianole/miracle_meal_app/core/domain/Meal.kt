package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

data class Meal (
    val id: Long,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val imageUrl: String?,
    val tags: List<String>,
    val youtube: String?,
    val ingredients: List<Ingredient>,
    val source: String?,
)

data class Ingredient (
    val ingredient: String?,
    val measure: String?,
)