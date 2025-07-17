package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Transaction
    @Query("""
        SELECT * FROM meal
    """)
    fun getMealStream(): Flow<List<MealWithIngredients>>

    @Transaction
    @Query("""
        SELECT * FROM meal WHERE id = :id
    """)
    suspend fun getMealById(id: Long): MealWithIngredients?

    @Transaction
    @Query("""
        DELETE FROM meal WHERE id = :id
    """)
    suspend fun deleteMealById(id: Long)

    suspend fun insertMeal(meal: Meal) {
        insertDbMeal(meal.toDb())
        insertDbIngredients(meal.ingredients.map { it.toDb(meal) })
    }

    @Insert
    suspend fun insertDbMeal(meal: DbMeal)

    @Insert
    suspend fun insertDbIngredients(ingredients: List<DbIngredient>)
}