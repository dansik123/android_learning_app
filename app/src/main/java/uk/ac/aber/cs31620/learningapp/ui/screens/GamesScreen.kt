package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.ui.components.GameItem
import uk.ac.aber.cs31620.learningapp.ui.icons.MyIconPack
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.Anagram
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.Quiz
import uk.ac.aber.cs31620.learningapp.ui.navigation.GameItemGroup
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.cardColor
import uk.ac.aber.cs31620.learningapp.ui.theme.contentBackgroundColor
import uk.ac.aber.cs31620.learningapp.ui.theme.primaryColor
import uk.ac.aber.cs31620.learningapp.utils.minTranslationCountToStartAnagram
import uk.ac.aber.cs31620.learningapp.utils.minTranslationCountToStartQuiz

@Composable
fun GamesScreen(
    navController: NavHostController,
){
    val availableGamesItemsList = listOf(
        GameItemGroup(
            MyIconPack.Anagram,
            NavRoutes.Anagram.route,
            stringResource(id = R.string.anagram),
            stringResource(
                id = R.string.anagram_game_desc,
                    minTranslationCountToStartAnagram
            ),
            minTranslationCountToStartAnagram
        ),
        GameItemGroup(
            MyIconPack.Quiz,
            NavRoutes.Quiz.route,
            stringResource(id = R.string.quiz),
            stringResource(
                id = R.string.quiz_game_desc,
                    minTranslationCountToStartQuiz
            ),
            minTranslationCountToStartQuiz
        )
    )

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.games)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(all = 10.dp),
        ) {
            items(items = availableGamesItemsList) { item ->
                GameItem(
                    modifier = Modifier
                        .padding(end = 4.dp, top = 4.dp),
                    gameGroupData = item,
                    cardColors = CardDefaults.cardColors(
                        containerColor = cardColor,
                        contentColor = contentBackgroundColor
                    ),
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
private fun GamesScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        GamesScreen(
            navController = navController
        )
    }
}