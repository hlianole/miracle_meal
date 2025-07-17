package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.look

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LookViewModel(
    private val mealRepository: MealRepository
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<LookScreenState>(LookScreenState.Loaded())
    val screenStateStream = _screenStateStream.asStateFlow()

    private val _queryStream = MutableStateFlow("")
    val queryStream = _queryStream.asStateFlow()

    fun search(query: String) {
        _queryStream.value = query

        if (query.isBlank()) {
            clearText()
        } else {
            _screenStateStream.value = LookScreenState.Loading
            viewModelScope.launch {
                val meals = mealRepository.getMealsByName(query)
                if (meals.success) {
                    _screenStateStream.value = LookScreenState.Loaded(
                        meals = meals.meals
                    )
                } else {
                    _screenStateStream.value = LookScreenState.Error
                }
            }
        }
    }

    fun clearText() {
        _queryStream.value = ""
        _screenStateStream.value = LookScreenState.Loaded(
            meals = emptyList()
        )
    }

    sealed interface LookScreenState {
        data object Loading: LookScreenState

        data object Error: LookScreenState

        data class Loaded(
            val meals: List<Meal> = emptyList()
        ): LookScreenState
    }
}