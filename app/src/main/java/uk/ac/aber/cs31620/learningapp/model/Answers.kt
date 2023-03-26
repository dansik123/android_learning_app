package uk.ac.aber.cs31620.learningapp.model

import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation

abstract class BasicAnswer(val question: String, val usersAnswer: String)

class CorrectAnswer(
    question: String,
    usersAnswer: String
): BasicAnswer(question, usersAnswer)

class IncorrectAnswer(
    question: String,
    usersAnswer: String,
    val correctAnswer: String
): BasicAnswer(question, usersAnswer)

fun getQuestionResultAnswer(
    questionTranslation: Translation,
    inputAnswer: String
): BasicAnswer{
    if (inputAnswer == questionTranslation.to) {
        return CorrectAnswer(questionTranslation.from, inputAnswer)
    }

    return IncorrectAnswer(
        questionTranslation.from,
        inputAnswer,
        questionTranslation.to
    )
}

