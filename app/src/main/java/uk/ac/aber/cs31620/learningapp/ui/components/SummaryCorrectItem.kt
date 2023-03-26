package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.model.BasicAnswer
import uk.ac.aber.cs31620.learningapp.model.CorrectAnswer
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.correctAnswerColor
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor

@Composable
fun SummaryCorrectItem(
    modifier: Modifier = Modifier,
    questionNo: Int,
    summaryCorrectItem: CorrectAnswer
) {
    Box(modifier = modifier.clip(RoundedCornerShape(10.dp)).
    background(color = secondaryColor)) {
        Column(
            modifier =
            Modifier.padding(all = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.answer_card_question_nr_text,
                        questionNo, summaryCorrectItem.question
                    ),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Icon(
                    imageVector = Icons.Filled.Check,
                    modifier = Modifier
                        .size(35.dp),
                    contentDescription = stringResource(id = R.string.correct),
                    tint = correctAnswerColor
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(
                    id = R.string.answer_card_user_answer,
                    summaryCorrectItem.usersAnswer
                )
            )
        }
    }
}

@Preview
@Composable
private fun LazyListSummaryCorrectItemPreview(){
    LearningAppTheme{
        SummaryCorrectItem(
            summaryCorrectItem = CorrectAnswer(
                "Question 1 with very big number of characters in it which creates so me problems to read",
                "User IncorrectAnswer"
            ),
            questionNo = 1
        )
    }
}