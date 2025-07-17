package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Ingredient
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealLocalDataSource(
    private val mealDao: MealDao,
) {
    fun getMealStream(): Flow<List<Meal>> = mealDao.getMealStream().map { mealWithIngredients ->
        mealWithIngredients.map {
            it.toDomain()
        }
    }

    suspend fun getMealById(id: Long): Meal? = mealDao.getMealById(id)?.toDomain()

    suspend fun insertMeal(meal: Meal) = mealDao.insertMeal(meal)

    suspend fun deleteMealById(id: Long) = mealDao.deleteMealById(id)

    private fun MealWithIngredients.toDomain(): Meal {
        return Meal(
            id = meal.id,
            name = meal.name,
            category = meal.category ?: R.string.no_category.toString(),
            area = meal.area ?: R.string.no_area.toString(),
            instructions = meal.instructions ?: R.string.no_instructions.toString(),
            imageUrl = meal.imageUrl,
            tags = meal.tags?.split(",") ?: listOf(R.string.no_tags.toString()),
            youtube = meal.youtube,
            ingredients = ingredients.map { it.toDomain() },
            source = meal.source,
        )
    }

    private fun DbIngredient.toDomain(): Ingredient {
        return Ingredient(
            ingredient = ingredient,
            measure = measure,
        )
    }
}