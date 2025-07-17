package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SavedViewModel(
    private val mealRepository: MealRepository
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<SavedScreenState>(SavedScreenState.Loading)
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            val meals = mealRepository.getMealStreamFromDb()
            _screenStateStream.value = if (meals.first().isEmpty()) {
                SavedScreenState.NoSaved
            } else {
                SavedScreenState.Loaded(meals = meals.first())
            }
        }
    }

    sealed interface SavedScreenState {
        data object Loading: SavedScreenState

        data object NoSaved: SavedScreenState

        data class Loaded(
            val meals: List<Meal>
        ): SavedScreenState
    }
}