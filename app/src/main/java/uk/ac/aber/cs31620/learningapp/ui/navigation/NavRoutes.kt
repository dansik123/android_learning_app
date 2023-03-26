package uk.ac.aber.cs31620.learningapp.ui.navigation

sealed class NavRoutes(val route: String) {
    object ChoseAppLanguages : NavRoutes("choseAppLanguages")
    object Home : NavRoutes("home")
    object Dictionary : NavRoutes("dictionary")
    object Games : NavRoutes("games")
    object Quiz: NavRoutes("quiz")
    object Anagram: NavRoutes("anagram")
    object AddNewWord: NavRoutes( "addNewWord")
    object SingleWordTranslation: NavRoutes( "singleWordTranslation")
    object EditTranslation: NavRoutes( "editTranslation")
    object QuizSelectQuestion: NavRoutes("quizSelectQuestion")
    object QuizTypeQuestion: NavRoutes("quizTypeQuestion")
    object QuizEnd: NavRoutes("quizEnd")
}

val navBottomBarRoutesList = listOf(
    NavRoutes.Home,
    NavRoutes.Dictionary,
    NavRoutes.Games
)

val appAvailableGamesRoutes = listOf(
    NavRoutes.Quiz,
    NavRoutes.Anagram
)