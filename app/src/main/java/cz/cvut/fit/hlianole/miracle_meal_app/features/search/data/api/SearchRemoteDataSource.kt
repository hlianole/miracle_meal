package cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.api

import cz.cvut.fit.hlianole.miracle_meal_app.core.data.api.ApiClient
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.domain.Category
import io.ktor.http.HttpMethod

class SearchRemoteDataSource(
    private val apiClient: ApiClient
) {
    suspend fun getAreas(): List<String> {
        val response = apiClient.request<AreaResponse>(
            endpoint = "list.php?a=list",
            method = HttpMethod.Get,
        )
        return response.meals.map { area ->
            area.strArea
        }
    }

    suspend fun getCategories(): List<Category> {
        val response = apiClient.request<CategoryResponse>(
            endpoint = "categories.php",
            method = HttpMethod.Get,
        )
        return response.categories.map { category ->
            Category(
                name = category.strCategory,
                imageUrl = category.strCategoryThumb
            )
        }
    }

    suspend fun getIngredients(): List<String> {
        val response = apiClient.request<IngredientResponse>(
            endpoint = "list.php?i=list",
            method = HttpMethod.Get,
        )
        return response.meals.map { ingredient ->
            ingredient.strIngredient
        }
    }
}