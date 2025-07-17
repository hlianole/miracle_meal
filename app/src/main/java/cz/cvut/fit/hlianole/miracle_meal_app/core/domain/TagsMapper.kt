package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

fun mapTags(str: String?): List<String> {
    return str?.split(",") ?: emptyList()
}