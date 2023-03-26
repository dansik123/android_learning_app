package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.model.IncorrectAnswer
import uk.ac.aber.cs31620.learningapp.model.BasicAnswer
import uk.ac.aber.cs31620.learningapp.model.CorrectAnswer
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme

@Composable
fun QuizAnswersSummaryList(
    modifier: Modifier = Modifier,
    quizAnswers: List<BasicAnswer> = emptyList()
) {
    Column(
        modifier = modifier
    ){
// Lazy column doesn't work
//        itemsIndexed(items = quizAnswers){
//            index, item ->
//            if(item is IncorrectAnswer){
//                SummaryIncorrectItem(
//                    questionNo = index + 1,
//                    summaryIncorrectItem = item,
//                )
//            }
//            else{
//                SummaryCorrectItem(
//                    questionNo = index + 1,
//                    summaryCorrectItem = item as CorrectAnswer
//                )
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//        }
        quizAnswers.forEachIndexed{
            index, item ->
            if(item is IncorrectAnswer){
                SummaryIncorrectItem(
                    questionNo = index + 1,
                    summaryIncorrectItem = item,
                )
            }
            else{
                SummaryCorrectItem(
                    questionNo = index + 1,
                    summaryCorrectItem = item as CorrectAnswer
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun QuizAnswersSummaryListPreview(){
    LearningAppTheme{
        QuizAnswersSummaryList(
            quizAnswers = listOf(
                CorrectAnswer(
                    "Question 1 with very big number of characters in it which creates so me problems to read",
                    "User IncorrectAnswer"
                ),
                IncorrectAnswer(
                    "Question 2 with very big number of characters in it which creates so me problems to read",
                    "User IncorrectAnswer",
                    "Correct Answer"
                ),
                IncorrectAnswer(
                    "Question 3 with very big number of characters in it which creates so me problems to read",
                    "User IncorrectAnswer",
                    "Correct Answer"
                ),
                CorrectAnswer(
                    "Question 1 with very big number of characters in it which creates so me problems to read",
                    "User IncorrectAnswer"
                ),
            )
        )
    }
}