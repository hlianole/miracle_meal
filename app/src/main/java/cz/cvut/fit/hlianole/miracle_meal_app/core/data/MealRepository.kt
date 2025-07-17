package cz.cvut.fit.hlianole.miracle_meal_app.core.data

import cz.cvut.fit.hlianole.miracle_meal_app.core.data.api.MealRemoteDataSource
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.db.MealLocalDataSource
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.MealResult
import kotlinx.coroutines.flow.Flow

// Main data source is API, but user
// has opportunity to save data to DB
class MealRepository(
    private val localDataSource: MealLocalDataSource,
    private val remoteDataSource: MealRemoteDataSource,
) {
    suspend fun getMealById(id: Long): Meal? {
        return try {
            remoteDataSource.getMealById(id)
        } catch (_: Throwable) {
            localDataSource.getMealById(id)
        }
    }

    suspend fun isMealSaved(id: Long): Boolean = localDataSource.getMealById(id) != null

    suspend fun deleteMealFromDb(id: Long) = localDataSource.deleteMealById(id)

    suspend fun saveMeal(meal: Meal) = localDataSource.insertMeal(meal)

    fun getMealStreamFromDb(): Flow<List<Meal>> = localDataSource.getMealStream()

    suspend fun getMealsByName(name: String): MealResult {
        return try {
            val meals = remoteDataSource.getMealsByName(
                name = name.replace(" ", "_")
            )
            MealResult(meals, true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }

    suspend fun getRandomMeal(): MealResult {
        return try {
            val meal = remoteDataSource.getRandomMeal() ?: throw RuntimeException()
            MealResult(listOf(meal), true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }

    suspend fun getMealsByCategory(category: String): MealResult {
        return try {
            val result = remoteDataSource.getMealsByCategory(category)
            MealResult(result, true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }

    suspend fun getMealsByArea(area: String): MealResult {
        return try {
            val result = remoteDataSource.getMealsByArea(area)
            MealResult(result, true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }

    suspend fun getMealsByFirstLetter(letter: String): MealResult {
        return try {
            val result = remoteDataSource.getMealsByFirstLetter(letter.lowercase())
            MealResult(result, true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }

    suspend fun getMealsByMainIngredient(ingredient: String): MealResult {
        return try {
            val result = remoteDataSource.getMealsByMainIngredient(ingredient)
            MealResult(result, true)
        } catch (_: Throwable) {
            MealResult(emptyList(), false)
        }
    }
}