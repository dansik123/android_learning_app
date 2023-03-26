package uk.ac.aber.cs31620.learningapp.datasource.dto

import androidx.lifecycle.LiveData
import androidx.room.*
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.entity.translationHelper.TranslationSelectQuestion

@Dao
interface TranslationDao {
    @Insert
    suspend fun insertSingleTranslation(translation: Translation)

    @Insert
    suspend fun insertMultipleTranslations(translationList: List<Translation>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTranslation(translation: Translation)

    @Query("DELETE FROM translations WHERE id = :id")
    suspend fun deleteTranslationById(id: Long)

    @Query("DELETE FROM translations")
    suspend fun deleteAllTranslations()

    @Query("""SELECT * FROM translations""")
    fun getAllTranslations(): LiveData<List<Translation>>

    @Query("""SELECT * FROM translations WHERE id = :id""")
    fun getTranslationById(id: Long?): LiveData<Translation>

//    query database search instead of filtering data in LazyColumn in []
//    @Query("""SELECT * FROM translations WHERE
//        translation_from LIKE '%' || :name || '%' or translation_to LIKE '%' || :name || '%'""")
//    fun getTranslationsByName(name: String?): List<TranslationData>

    @Query("""SELECT * FROM translations ORDER BY RANDOM() LIMIT 1""")
    fun getRandomTranslation(): LiveData<Translation>

//    @Query("""SELECT translation_to FROM translations
//        WHERE id <> :correctTranslationId ORDER BY RANDOM() LIMIT 3""")
//    fun getThreeRandomSelectableAnswersExceptCorrect(correctTranslationId: Long): LiveData<List<String>>

    @Query("""SELECT t1.id, t1.translation_from, t1.translation_to, t2.fake_answers 
        FROM (SELECT id, translation_from, translation_to FROM translations WHERE id = :randomTranslationId) t1, 
        (SELECT group_concat(otherAnswers.answer, ',') AS fake_answers 
        FROM (SELECT t_f.translation_to AS answer FROM translations t_f WHERE t_f.id != :randomTranslationId ORDER BY RANDOM() LIMIT 3) otherAnswers) t2
    """)
    fun getSelectableQuestion(randomTranslationId: Long?): LiveData<TranslationSelectQuestion>

    @Query("""SELECT id FROM translations ORDER BY RANDOM() LIMIT 1""")
    fun getRandomTranslationId(): LiveData<Long>

    @Query("""SELECT COUNT(*) FROM translations""")
    fun getTranslationsCount(): LiveData<Int>
}