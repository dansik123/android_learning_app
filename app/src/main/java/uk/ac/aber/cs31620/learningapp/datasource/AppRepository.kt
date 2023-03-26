package uk.ac.aber.cs31620.learningapp.datasource

import android.app.Application
import androidx.lifecycle.LiveData
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.dto.TranslationDao
import uk.ac.aber.cs31620.learningapp.datasource.entity.translationHelper.TranslationSelectQuestion

class AppRepository(application: Application) {
    private val translationDao: TranslationDao =
        AppRoomDatabase.getDatabase(application)!!.translationDao()

    suspend fun insert(translation: Translation){
        translationDao.insertSingleTranslation(translation)
    }

//    suspend fun insertMultipleCats(translations: List<TranslationData>){
//        translationDao.insertMultipleTranslations(translations)
//    }

    suspend fun updateTranslation(translation: Translation){
        translationDao.updateTranslation(translation)
    }

    suspend fun deleteTranslation(id: Long){
        translationDao.deleteTranslationById(id)
    }

    suspend fun deleteAllTranslations() = translationDao.deleteAllTranslations()

    fun getAllTranslations(): LiveData<List<Translation>> = translationDao.getAllTranslations()

    fun getTranslationById(id: Long): LiveData<Translation> {
        return translationDao.getTranslationById(id)
    }

    fun getRandomTranslation() = translationDao.getRandomTranslation()

    fun getSelectableQuizQuestion(id: Long?): LiveData<TranslationSelectQuestion>{
        return translationDao.getSelectableQuestion(id)
    }

    fun getRandomTranslationId(): LiveData<Long>{
        return translationDao.getRandomTranslationId()
    }

    fun getTranslationsCount(): LiveData<Int> = translationDao.getTranslationsCount()

}