package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.BasicAnswer
import uk.ac.aber.cs31620.learningapp.model.QuizAnswersSingleton
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.model.getQuestionResultAnswer
import uk.ac.aber.cs31620.learningapp.ui.components.TranslationTextComponent
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.*

@Composable
fun QuizSelectQuestionScreen(
    navController: NavHostController,
    fromLanguages: Languages,
    translationsViewModel: TranslationViewModel = viewModel(),
    questionsNumber: Int = 1
) {
    val randomTranslationId by translationsViewModel.randomTranslationId.observeAsState()
    val selectQuizQuestion by translationsViewModel.selectQuestion.observeAsState()
    val selectPossibleAnswers: SnapshotStateList<String> = remember{ mutableStateListOf() }
    val selectedAnswer: MutableState<String> = rememberSaveable{ mutableStateOf("") }
    val quizQuestionStr =  remember{ mutableStateOf("") }
    randomTranslationId?.let{
        translationId ->
        translationsViewModel.getSelectedQuizQuestion(translationId)
    }
    selectQuizQuestion?.let{
        selectQuestion ->
        if(selectPossibleAnswers.isEmpty()) {
            val selectAvailableAnswers = mutableListOf<String>()
            selectAvailableAnswers.addAll(selectQuestion.fakeAnswers.split(","))
            selectAvailableAnswers.add(selectQuestion.translation.to)
            selectPossibleAnswers.addAll(selectAvailableAnswers.shuffled())
            quizQuestionStr.value = selectQuestion.translation.from
        }
    }

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.quiz_title),
    ) {
        ConstraintLayout {
            val (question_area, select_input, submit_btn) = createRefs()
            Column(
                modifier =
                Modifier.constrainAs(question_area) {
                    top.linkTo(parent.top, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = stringResource(
                        R.string.quiz_type_question_title,
                        fromLanguages.translated
                    ),
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(5.dp))

                TranslationTextComponent(
                    translationLanguage = fromLanguages.original,
                    translationWord = quizQuestionStr.value,
                    isOriginalLanguage = true,
                )
            }

            SelectableAnswers(
                modifier =
                Modifier
                    .constrainAs(select_input) {
                        top.linkTo(question_area.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(0.9f),
                answers = selectPossibleAnswers,
                selectedAnswer = selectedAnswer
            )

            Button(
                onClick = {
                    val answer: BasicAnswer =
                        getQuestionResultAnswer(
                            selectQuizQuestion!!.translation,
                            selectedAnswer.value
                        )
                    QuizAnswersSingleton.addNewQuizAnswer(answer)
                    if(QuizAnswersSingleton.getQuizAnswers().size < questionsNumber){
                        navController.navigate(route = NavRoutes.QuizTypeQuestion.route + "/${questionsNumber}")
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
                        top.linkTo(select_input.bottom)
                        end.linkTo(parent.end, margin = 10.dp)
                    }
            ) {
                Text(stringResource(id = R.string.next))
            }
        }
    }
}

@Composable
private fun SelectableAnswers(
    modifier: Modifier = Modifier,
    answers: List<String>,
    selectedAnswer: MutableState<String>,
    itemHeight: Dp = 45.dp,
    totalSpaceBetweenItems: Dp = 40.dp
){
    val columnHeight: Dp = (itemHeight.times(answers.size)) + totalSpaceBetweenItems
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.height(columnHeight),
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        items(items = answers){
            item ->
            val selected: Boolean = item == selectedAnswer.value
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
                    .clip(RoundedCornerShape(10.dp))
                    .selectable(
                        selected = selected,
                        onClick = {
                            selectedAnswer.value = item
                        }
                    )
                    .background(
                        if(selected) primaryColor else secondaryColor),
                contentAlignment = Alignment.Center
            ){
                Text(
                    item,
                    color = (
                        if(selected) secondaryColor else nonPrimaryTextColor)
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuizSelectQuestionScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        QuizSelectQuestionScreen(
            navController,
            fromLanguages = Languages("English", "Polish")
        )
    }
}

@Preview
@Composable
private fun SelectableAnswersPreview(){
    LearningAppTheme{
        SelectableAnswers(
            answers = listOf("Answer1", "Answer2","Answer3", "Answer4"),
            selectedAnswer = rememberSaveable{ mutableStateOf("Answer1") }
        )
    }
}