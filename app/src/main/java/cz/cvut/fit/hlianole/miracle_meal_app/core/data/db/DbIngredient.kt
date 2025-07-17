package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Ingredient
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal

@Entity(
    tableName = "ingredient",
    foreignKeys = [
        ForeignKey(
            entity = DbMeal::class,
            parentColumns = ["id"],
            childColumns = ["idMeal"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class DbIngredient (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idMeal: Long,
    val ingredient: String?,
    val measure: String?
)

fun Ingredient.toDb(meal: Meal): DbIngredient {
    return DbIngredient(
        idMeal = meal.id,
        ingredient = ingredient,
        measure = ingredient,
    )
}