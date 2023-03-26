package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.*
import uk.ac.aber.cs31620.learningapp.ui.components.TranslationTextComponent
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor

@Composable
fun QuizTypeQuestionScreen(
    navController: NavHostController,
    fromLanguages: Languages,
    translationsViewModel: TranslationViewModel = viewModel(),
    questionsNumber: Int = 1
) {
    val randomTranslation by
        translationsViewModel.singleRandomTranslation.observeAsState(
            Translation("","")
        )
    val typedAnswer = rememberSaveable{ mutableStateOf("") }

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.quiz_title),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            val (title_text, original_translation, answer_input, submit_btn) = createRefs()
            Text(
                text = stringResource(
                    R.string.quiz_type_question_title,
                    fromLanguages.translated
                ),
                fontSize = 20.sp,
                modifier =
                Modifier.constrainAs(title_text) {
                    top.linkTo(parent.top, margin = 25.dp)
                    start.linkTo(parent.start)
                }
            )

            TranslationTextComponent(
                translationLanguage = fromLanguages.original,
                translationWord = randomTranslation.from,
                isOriginalLanguage = true,
                modifier =
                Modifier.constrainAs(original_translation) {
                    top.linkTo(title_text.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                }
            )

            OutlinedTextField(value = typedAnswer.value,
                onValueChange = {
                    typedAnswer.value = it
                },
                label = {
                    Text(
                        text = stringResource(
                            R.string.one_translation_translated_text_label,
                            fromLanguages.translated
                        )
                    )
                },
                modifier =
                Modifier.constrainAs(answer_input) {
                    top.linkTo(original_translation.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            typedAnswer.value = ""
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            modifier = Modifier.size(20.dp),
                            contentDescription =
                            stringResource(id = R.string.cancel),
                        )
                    }
                }
            )

            Button(
                onClick = {
                    val answer: BasicAnswer =
                        getQuestionResultAnswer(randomTranslation, typedAnswer.value)
                    QuizAnswersSingleton.addNewQuizAnswer(answer)
                    if(QuizAnswersSingleton.getQuizAnswers().size < questionsNumber){
                        navController.navigate(
                            route = NavRoutes.QuizSelectQuestion.route +
                                    "/${questionsNumber}"
                        )
                    }else {
                        navController.navigate(route = NavRoutes.QuizEnd.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiaryColor,
                    contentColor = secondaryColor
                ),
                modifier =
                Modifier.constrainAs(submit_btn) {
                    top.linkTo(answer_input.bottom, margin = 10.dp)
                    end.linkTo(parent.end)
                }
            ) {
                Text(stringResource(id = R.string.next))
            }
        }
    }
}

@Preview
@Composable
private fun QuizTypeQuestionScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        QuizTypeQuestionScreen(
            navController,
            fromLanguages = Languages("English", "Polish")
        )
    }
}