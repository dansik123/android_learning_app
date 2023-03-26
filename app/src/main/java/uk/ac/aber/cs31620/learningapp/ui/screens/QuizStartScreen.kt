package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.model.QuizAnswersSingleton
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.*

@Composable
fun QuizStartScreen(navController: NavHostController) {
    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.quiz_title),
    ){
        val dialogBooleanState = rememberSaveable{ mutableStateOf(true) }
        QuizAnswersSingleton.resetQuizAnswers()
        QuizStartDialog(
            navController = navController,
            isOpen = dialogBooleanState.value,
            isDialogOpenState = {
                dialogBooleanState.value = it
            }
        )
    }
}

@Composable
private fun QuizStartDialog(
    navController: NavHostController,
    numberOfQuestions: Int = 1,
    isOpen: Boolean,
    isDialogOpenState: (Boolean)->Unit = {}
) {
    var dialogQuizQuestionsNumber by
        remember { mutableStateOf(numberOfQuestions.toFloat()) }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = stringResource(id = R.string.quiz_title))
            },
            text = {
                ConstraintLayout {
                    val (dialogText, dialogQuestionsInput) = createRefs()

                    Text(
                        text = stringResource(
                            id = R.string.quiz_dialog_desc
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .constrainAs(dialogText){
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                    )

                    Column(
                        modifier = Modifier.
                        constrainAs(dialogQuestionsInput){
                            top.linkTo(dialogText.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    ) {
                        Slider(
                            value = dialogQuizQuestionsNumber,
                            onValueChange = { dialogQuizQuestionsNumber = it },
                            valueRange = 1f..20f,
                            colors = SliderDefaults.colors(
                                thumbColor = primaryColor
                            )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.questions,
                                dialogQuizQuestionsNumber.toInt()
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogOpenState(false)
                        navController.navigate(
                            route = NavRoutes.QuizTypeQuestion.route
                                    + "/${dialogQuizQuestionsNumber.toInt()}"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(stringResource(id = R.string.start))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogOpenState(false)
                        navController.navigate(route = NavRoutes.Games.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Preview
@Composable
private fun QuizStartScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        QuizStartScreen(navController)
    }
}