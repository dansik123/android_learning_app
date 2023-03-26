package uk.ac.aber.cs31620.learningapp.datasource.entity.translationHelper

import androidx.room.ColumnInfo
import androidx.room.Embedded
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation

data class TranslationSelectQuestion(
    @Embedded
    val translation: Translation,

    @ColumnInfo(name = "fake_answers")
    val fakeAnswers: String
)
