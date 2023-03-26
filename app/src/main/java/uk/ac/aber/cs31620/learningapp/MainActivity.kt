package uk.ac.aber.cs31620.learningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.datasource.store.AppLanguagesStore
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.screens.*
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningAppTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val appLanguagesStore = AppLanguagesStore(context)
    val appLanguagesState: State<Languages> = appLanguagesStore.getAppLanguages.collectAsState(
        initial = Languages("undefined","undefined"))


    NavHost(
        navController = navController,
        startDestination = (
                if (appLanguagesState.value.hasLanguages()) NavRoutes.Home.route
                else NavRoutes.ChoseAppLanguages.route
        )
    ) {
        composable(NavRoutes.ChoseAppLanguages.route) { HomeScreen(navController, true) }
        composable(NavRoutes.Home.route) { HomeScreen(navController) }
        composable(NavRoutes.Dictionary.route) { DictionaryListScreen(navController) }
        composable(NavRoutes.Quiz.route) { QuizStartScreen(navController) }
        composable(NavRoutes.Anagram.route) { AnagramGameScreen(navController, appLanguagesState.value) }
        composable(NavRoutes.AddNewWord.route) { NewWordScreen(navController, appLanguagesState.value) }
        composable(NavRoutes.Games.route) { GamesScreen(navController) }
        composable(NavRoutes.SingleWordTranslation.route + "/{id}") {
            navBackStack ->
                // Extracting the argument
                val singleTranslationId: Long? = navBackStack.arguments?.getString("id")
                    ?.toLongOrNull()

                // Pass the extracted singleTranslationId to screen
                SingleTranslationScreen(
                    navController,
                    translationId = singleTranslationId!!,
                    appLanguagesState.value
                )
        }
        composable(NavRoutes.EditTranslation.route + "/{id}") {
            navBackStack ->
                // Extracting the argument
                val singleTranslationId: Long? = navBackStack.arguments?.getString("id")
                    ?.toLongOrNull()
                EditSingleTranslationScreen(
                    navController,
                    translationId = singleTranslationId!!,
                    appLanguagesState.value
                )
        }
        composable(NavRoutes.QuizSelectQuestion.route + "/{quizQuestionsNumber}") {
            navBackStack ->
                val questionsNumber: Int = navBackStack.arguments?.getString("quizQuestionsNumber")?.toInt()!!
                QuizSelectQuestionScreen(
                    navController,
                    fromLanguages = appLanguagesState.value,
                    questionsNumber = questionsNumber
                )
        }
        composable(NavRoutes.QuizTypeQuestion.route + "/{quizQuestionsNumber}") {
            navBackStack ->
                val questionsNumber: Int = navBackStack.arguments?.getString("quizQuestionsNumber")?.toInt()!!
                QuizTypeQuestionScreen(
                    navController,
                    fromLanguages = appLanguagesState.value,
                    questionsNumber = questionsNumber
                )
        }
        composable(NavRoutes.QuizEnd.route) {
            QuizEndScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearningAppTheme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}
