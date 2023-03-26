package uk.ac.aber.cs31620.learningapp.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.dto.TranslationDao

@Database(entities = [Translation::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase(){
    abstract fun translationDao(): TranslationDao

    companion object {
        private var instance: AppRoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppRoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        "translation_database"
                    )
                        .allowMainThreadQueries()
                        .build()
            }
            return instance
        }
    }
}