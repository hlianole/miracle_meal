package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithIngredients (
    @Embedded val meal: DbMeal,
    @Relation(
        parentColumn = "id",
        entityColumn = "idMeal",
    )
    val ingredients: List<DbIngredient>,
)