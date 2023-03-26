package uk.ac.aber.cs31620.learningapp.model

object QuizAnswersSingleton {
    private var quizAnswers: MutableList<BasicAnswer> = mutableListOf()

    fun resetQuizAnswers(){
        quizAnswers = mutableListOf()
    }

    fun addNewQuizAnswer(answer: BasicAnswer){
        quizAnswers.add(answer)
    }

    fun getQuizAnswers(): MutableList<BasicAnswer>{
        return quizAnswers
    }

    fun getQuizCorrectAnswersCount(): Int{
        return quizAnswers.count { it !is IncorrectAnswer }
    }

    fun getQuizAnswersTotalCount(): Int{
        return quizAnswers.size
    }
}