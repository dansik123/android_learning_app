package uk.ac.aber.cs31620.learningapp.utils

import androidx.navigation.NavController
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes

private val gameRouteScreenList: List<String> = listOf(
    NavRoutes.QuizTypeQuestion.route,
    NavRoutes.QuizSelectQuestion.route
)

fun isInGameScreen(navController: NavController): Boolean{
    val currentDestination = navController.currentDestination
    return gameRouteScreenList.any {
        gameRoute ->
        currentDestination?.route?.startsWith(prefix = gameRoute) == true
    }
}

const val minTranslationCountToStartAnagram = 3
const val minTranslationCountToStartQuiz = 8