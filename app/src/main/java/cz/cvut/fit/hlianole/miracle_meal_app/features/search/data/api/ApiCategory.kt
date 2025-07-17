package cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiCategory(
    val strCategory: String,
    val strCategoryThumb: String
)