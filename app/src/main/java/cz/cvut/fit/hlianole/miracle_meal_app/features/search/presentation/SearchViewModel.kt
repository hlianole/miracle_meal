package cz.cvut.fit.hlianole.miracle_meal_app.features.search.presentation

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.hlianole.miracle_meal_app.R
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.FilterType
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.data.SearchRepository
import cz.cvut.fit.hlianole.miracle_meal_app.features.search.domain.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val resources: Resources
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        onFilterButtonClick(FilterType.Categories)
    }

    fun getIngredientImageUrl (name: String): String {
        return resources.getString(R.string.www_themealdb_com_images_ingredients) + name
            .split(" ")
            .joinToString("_") {
                it.lowercase()
            } + resources.getString(R.string.medium_png)
    }

    fun onFilterButtonClick(filterType: FilterType) {
        val currentState = _screenStateStream.value

        when (filterType) {
            FilterType.Categories -> if (currentState is SearchScreenState.CategoryState) return
            FilterType.FirstLetter -> if (currentState is SearchScreenState.FirstLetter) return
            FilterType.Areas -> if (currentState is SearchScreenState.Area) return
            FilterType.Ingredients -> if (currentState is SearchScreenState.MainIngredient) return
        }

        viewModelScope.launch {
            _screenStateStream.value = SearchScreenState.Loading

            val result = when (filterType) {
                FilterType.Categories -> {
                    val categoryResult = searchRepository.getCategories()
                    if (categoryResult.success) {
                        SearchScreenState.CategoryState(categoryResult.categories)
                    } else {
                        SearchScreenState.Error
                    }
                }
                FilterType.Areas -> {
                    val areaResult = searchRepository.getAreas()
                    if (areaResult.success) {
                        SearchScreenState.Area(areaResult.result)
                    } else {
                        SearchScreenState.Error
                    }
                }
                FilterType.FirstLetter -> {
                    SearchScreenState.FirstLetter(
                        resources.getString(R.string.alphabet).split(",")
                    )
                }
                FilterType.Ingredients -> {
                    val ingredientResult = searchRepository.getIngredients()
                    if (ingredientResult.success) {
                        SearchScreenState.MainIngredient(ingredientResult.result)
                    } else {
                        SearchScreenState.Error
                    }
                }
            }

            _screenStateStream.value = result
        }
    }

    sealed interface SearchScreenState {
        data object Loading: SearchScreenState

        data object Error: SearchScreenState

        data class FirstLetter(
            val letters: List<String>
        ): SearchScreenState

        data class CategoryState(
            val categories: List<Category>
        ): SearchScreenState

        data class Area(
            val ares: List<String>
        ): SearchScreenState

        data class MainIngredient(
            val ingredients: List<String>
        ): SearchScreenState
    }
}