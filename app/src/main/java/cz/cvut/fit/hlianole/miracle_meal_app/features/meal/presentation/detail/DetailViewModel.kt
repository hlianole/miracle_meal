package cz.cvut.fit.hlianole.miracle_meal_app.features.meal.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.cvut.fit.hlianole.miracle_meal_app.Screens
import cz.cvut.fit.hlianole.miracle_meal_app.core.data.MealRepository
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.AnalyticsHelper
import cz.cvut.fit.hlianole.miracle_meal_app.core.domain.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository,
): ViewModel() {
    private val _screenStateStream = MutableStateFlow<DetailScreenState>(DetailScreenState.Loading)
    val screenStateStream get() = _screenStateStream.asStateFlow()

    init {
        AnalyticsHelper.logEvent("screen viewed", mapOf(
            "screen name" to "detail screen")
        )
        viewModelScope.launch {
            _screenStateStream.value = DetailScreenState.Loading
            val id = savedStateHandle.toRoute<Screens.MealDetail>().id
            val meal = mealRepository.getMealById(id)
            if (meal == null) {
                _screenStateStream.value = DetailScreenState.Error
            } else {
                val saved = mealRepository.isMealSaved(id)
                _screenStateStream.value = DetailScreenState.Loaded(
                    meal = meal,
                    saved = saved
                )
            }
        }
    }

    fun onSaveClick() {
        if (_screenStateStream.value is DetailScreenState.Loaded) {
            viewModelScope.launch {
                val saved = (_screenStateStream.value as DetailScreenState.Loaded).saved
                val meal = (_screenStateStream.value as DetailScreenState.Loaded).meal
                _screenStateStream.value = DetailScreenState.Loaded(
                    meal = meal,
                    saved = !saved,
                )
                if (saved) {
                    mealRepository.deleteMealFromDb((_screenStateStream.value as DetailScreenState.Loaded).meal!!.id)
                } else {
                    mealRepository.saveMeal((_screenStateStream.value as DetailScreenState.Loaded).meal!!)
                }
            }
        }
    }

    sealed interface DetailScreenState {
        data object Loading: DetailScreenState

        data object Error: DetailScreenState

        data class Loaded (
            val meal: Meal? = null,
            val saved: Boolean = false,
        ): DetailScreenState
    }
}