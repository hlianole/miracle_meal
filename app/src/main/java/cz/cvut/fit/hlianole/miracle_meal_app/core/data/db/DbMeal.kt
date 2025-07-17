package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal

@Entity(tableName = "meal")
data class DbMeal (
    @PrimaryKey val id: Long,
    val name: String,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val imageUrl: String?,
    val tags: String?,
    val youtube: String?,
    val source: String?,
)

fun Meal.toDb(): DbMeal {
    return DbMeal(
        id = id,
        name = name,
        category = category,
        area = area,
        instructions = instructions,
        imageUrl = imageUrl,
        tags = tags.joinToString(separator = ","),
        youtube = youtube,
        source = source,
    )
}