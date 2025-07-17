package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val screenStateStream = _screenStateStream.asStateFlow()

    private lateinit var state: Screens.SearchStates

    init {
        viewModelScope.launch {
            state = savedStateHandle.toRoute<Screens.MealList>().state
            val name = savedStateHandle.toRoute<Screens.MealList>().name
            val result = when (state) {
                Screens.SearchStates.Category -> {
                    mealRepository.getMealsByCategory(name)
                }

                Screens.SearchStates.Area -> {
                    mealRepository.getMealsByArea(name)
                }

                Screens.SearchStates.FirstLetter -> {
                    mealRepository.getMealsByFirstLetter(name)
                }

                Screens.SearchStates.MainIngredient -> {
                    mealRepository.getMealsByMainIngredient(name)
                }
            }
            _screenStateStream.value = if (!result.success) {
                ListScreenState.Error
            } else {
                ListScreenState.Loaded(
                    name = name,
                    meals = result.meals
                )
            }
        }
    }

    sealed interface ListScreenState {
        data object Loading: ListScreenState

        data object Error: ListScreenState

        data class Loaded (
            val name: String,
            val meals: List<Meal>
        ): ListScreenState
    }
}