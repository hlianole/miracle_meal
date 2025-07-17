package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

import cz.cvut.fit.hlianole.miracle_meal_app.core.data.api.ApiMeal

fun mapIngredients(apiMeal: ApiMeal): List<Ingredient> {
    return listOf(
        Ingredient(apiMeal.strIngredient1, apiMeal.strMeasure1),
        Ingredient(apiMeal.strIngredient2, apiMeal.strMeasure2),
        Ingredient(apiMeal.strIngredient3, apiMeal.strMeasure3),
        Ingredient(apiMeal.strIngredient4, apiMeal.strMeasure4),
        Ingredient(apiMeal.strIngredient5, apiMeal.strMeasure5),
        Ingredient(apiMeal.strIngredient6, apiMeal.strMeasure6),
        Ingredient(apiMeal.strIngredient7, apiMeal.strMeasure7),
        Ingredient(apiMeal.strIngredient8, apiMeal.strMeasure8),
        Ingredient(apiMeal.strIngredient9, apiMeal.strMeasure9),
        Ingredient(apiMeal.strIngredient10, apiMeal.strMeasure10),
        Ingredient(apiMeal.strIngredient11, apiMeal.strMeasure11),
        Ingredient(apiMeal.strIngredient12, apiMeal.strMeasure12),
        Ingredient(apiMeal.strIngredient13, apiMeal.strMeasure13),
        Ingredient(apiMeal.strIngredient14, apiMeal.strMeasure14),
        Ingredient(apiMeal.strIngredient15, apiMeal.strMeasure15),
        Ingredient(apiMeal.strIngredient16, apiMeal.strMeasure16),
        Ingredient(apiMeal.strIngredient17, apiMeal.strMeasure17),
        Ingredient(apiMeal.strIngredient18, apiMeal.strMeasure18),
        Ingredient(apiMeal.strIngredient19, apiMeal.strMeasure19),
        Ingredient(apiMeal.strIngredient20, apiMeal.strMeasure20),
    ).filter { ingredient ->
        ingredient.ingredient != null && ingredient.measure != null &&
        ingredient.ingredient != "" && ingredient.measure != ""
    }
}