package cz.cvut.fit.hlianole.miracle_meal_app.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        DbMeal::class,
        DbIngredient::class,
    ]
)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        fun newInstance(context: Context): MealDatabase {
            return Room.databaseBuilder(
                context,
                MealDatabase::class.java,
                "meal.db",
            ).build()
        }
    }
}