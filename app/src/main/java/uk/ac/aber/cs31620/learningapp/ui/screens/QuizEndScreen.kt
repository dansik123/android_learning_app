package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.model.QuizAnswersSingleton
import uk.ac.aber.cs31620.learningapp.ui.components.QuizAnswersSummaryList
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.*

@Composable
fun QuizEndScreen(
    navController: NavHostController,
    quizAnswersSingleton: QuizAnswersSingleton = QuizAnswersSingleton) {
    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.quiz_title),
    ) {
        val coroutineScope = rememberCoroutineScope()

        val answersList = quizAnswersSingleton.getQuizAnswers()
        val correctAnswers: Int = quizAnswersSingleton.getQuizCorrectAnswersCount()
        val totalAnswers: Int = quizAnswersSingleton.getQuizAnswersTotalCount()

        val scrollState = rememberScrollState()
        var scrollToPosition = 0

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.end_quiz_title),
                fontSize = 28.sp,
                modifier = Modifier.padding(10.dp)
            )
            ScoreComponent(correct = correctAnswers , total = totalAnswers)

            Spacer(modifier = Modifier.height(20.dp))

            ExpandLikeBtn(
                expandMore = true,
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        scrollToPosition = (scrollState.value + coordinates.positionInRoot().y).toInt()
                    },
                onClick = {
                    coroutineScope.launch {
                        scrollState.scrollTo(scrollToPosition)
                    }
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            QuizAnswersSummaryList(
                modifier = Modifier.fillMaxWidth(0.9f),
                quizAnswers = answersList
            )

            ExpandLikeBtn(
                expandMore = false,
                onClick = {
                    coroutineScope.launch {
                        scrollState.scrollTo(0) //scroll back to the top of summary
                    }
                }
            )
        }
    }
}

@Composable
private fun ExpandLikeBtn(
    modifier: Modifier = Modifier,
    expandMore: Boolean = false,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = primaryColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = (
                    if (expandMore) Icons.Filled.ExpandMore
                    else Icons.Filled.ExpandLess
                ),
                contentDescription =
                    stringResource(
                        id = (
                            if (expandMore) R.string.expand_more
                            else R.string.expand_less
                        )
                    )
            )
            Text(
                text = stringResource(
                    id = (
                        if (expandMore) R.string.expand_more
                        else R.string.expand_less
                    )
                )
            )
        }
    }
}

@Composable
private fun ScoreComponent(
    modifier: Modifier = Modifier,
    correct: Int = 0,
    total: Int = 0
) {
    Box(
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.finish),
            contentDescription = stringResource(id = R.string.finish_image),
            contentScale = ContentScale.FillWidth,
            modifier =
            Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center)
        )
        Text(
            text = stringResource(
                    id = R.string.quiz_summary_text, correct, total
                ),
            modifier =
                Modifier.
                    align(Alignment.BottomCenter),
            fontSize = 24.sp
        )
    }
}


@Preview
@Composable
private fun ExpandLikeBtnUpPreview(){
    LearningAppTheme{
        ExpandLikeBtn(expandMore = true)
    }
}

@Preview
@Composable
private fun ExpandLikeBtnDownPreview(){
    LearningAppTheme{
        ExpandLikeBtn(expandMore = false)
    }
}

@Preview
@Composable
private fun QuizEndScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        QuizEndScreen(navController)
    }
}