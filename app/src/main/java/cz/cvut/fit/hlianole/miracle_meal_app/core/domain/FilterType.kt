package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

sealed class FilterType {
    data object Categories : FilterType()
    data object FirstLetter : FilterType()
    data object Areas : FilterType()
    data object Ingredients : FilterType()
}