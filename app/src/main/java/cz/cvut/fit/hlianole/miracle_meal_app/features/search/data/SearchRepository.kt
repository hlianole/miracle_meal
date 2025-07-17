package cz.cvut.fit.hlianole.miracle_meal_app.features.search.data

import cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.api.SearchRemoteDataSource
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.domain.CategoryResult
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.domain.StringResult

class SearchRepository(
    private val searchRemoteDataSource: SearchRemoteDataSource
) {
    suspend fun getAreas(): StringResult {
        return try {
            val result = searchRemoteDataSource.getAreas()
            StringResult(result, true)
        } catch (_: Throwable) {
            StringResult(emptyList(), false)
        }
    }

    suspend fun getCategories(): CategoryResult {
        return try {
            val result = searchRemoteDataSource.getCategories()
            CategoryResult(result, true)
        } catch (_: Throwable) {
            CategoryResult(emptyList(), false)
        }
    }

    suspend fun getIngredients(): StringResult {
        return try {
            val result = searchRemoteDataSource.getIngredients()
            StringResult(result, true)
        } catch (_: Throwable) {
            StringResult(emptyList(), false)
        }
    }
}