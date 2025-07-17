package cz.cvut.fit.hlianole.miracle_meal_app.core.data.api

import android.content.res.Resources
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.mapIngredients
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.mapTags
import io.ktor.http.HttpMethod

class MealRemoteDataSource (
    private val apiClient: ApiClient,
    private val resources: Resources
) {
    suspend fun getMealById(
        id: Long,
    ): Meal? {
        val response = apiClient.request<MealResponse>(
            endpoint = "lookup.php?i=$id",
            method = HttpMethod.Get,
        )
        return mapApiMeals(response.meals).firstOrNull()
    }

    suspend fun getMealsByName(name: String): List<Meal> {
        val response = apiClient.request<MealResponse>(
            endpoint = "search.php?s=$name",
            method = HttpMethod.Get
        )
        return mapApiMeals(response.meals)
    }

    suspend fun getRandomMeal(): Meal? {
        val response = apiClient.request<MealResponse>(
            endpoint = "random.php",
            method = HttpMethod.Get,
        )
        return mapApiMeals(response.meals).firstOrNull()
    }

    suspend fun getMealsByCategory(
        category: String,
    ): List<Meal> {
        val response = apiClient.request<MealShortResponse>(
            endpoint = "filter.php?c=$category",
            method = HttpMethod.Get
        )
        return response.meals.mapNotNull { shortMeal ->
            getMealById(shortMeal.idMeal)
        }
    }

    suspend fun getMealsByArea(
        area: String,
    ): List<Meal> {
        val response = apiClient.request<MealShortResponse>(
            endpoint = "filter.php?a=$area",
            method = HttpMethod.Get
        )
        return response.meals.mapNotNull { shortMeal ->
            getMealById(shortMeal.idMeal)
        }
    }

    suspend fun getMealsByMainIngredient(
        ingredient: String,
    ): List<Meal> {
        val response = apiClient.request<MealShortResponse>(
            endpoint = "filter.php?i=${ingredient.toSnakeCase()}",
            method = HttpMethod.Get
        )
        return response.meals.mapNotNull { shortMeal ->
            getMealById(shortMeal.idMeal)
        }
    }

    suspend fun getMealsByFirstLetter(
        letter: String,
    ): List<Meal> {
        val response = apiClient.request<MealResponse>(
            endpoint = "search.php?f=$letter",
            method = HttpMethod.Get,
        )
        return mapApiMeals(response.meals)
    }

    private fun String.toSnakeCase(): String {
        return this
            .split(" ")
            .joinToString("_") {
                it.lowercase()
            }
    }

    private fun mapApiMeals(apiMeals: List<ApiMeal>): List<Meal> {
        return apiMeals.map { meal ->
            Meal(
                id = meal.idMeal,
                name = meal.strMeal,
                category = meal.strCategory ?: resources.getString(R.string.no_category),
                area = meal.strArea ?: resources.getString(R.string.no_area),
                instructions = meal.strInstructions ?: resources.getString(R.string.no_instructions),
                imageUrl = meal.strMealThumb,
                tags = mapTags(meal.strTags),
                youtube = meal.strYoutube,
                ingredients = mapIngredients(meal),
                source = meal.strSource,
            )
        }
    }
}