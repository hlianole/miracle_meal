package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.MealResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mealRepository: MealRepository
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        generateNew()
    }

    fun generateNew() {
        viewModelScope.launch {
            val result = mealRepository.getRandomMeal()
            if (result.success) {
                _screenStateStream.value = HomeScreenState.Loaded(result)
            } else {
                _screenStateStream.value = HomeScreenState.Error
            }
        }
    }

    sealed interface HomeScreenState {
        data object Loading: HomeScreenState

        data object Error: HomeScreenState

        data class Loaded(
            val mealResult: MealResult
        ): HomeScreenState
    }
}