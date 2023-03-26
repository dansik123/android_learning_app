package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.model.IncorrectAnswer
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.incorrectAnswerColor
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor

@Composable
fun SummaryIncorrectItem(
    modifier: Modifier = Modifier,
    questionNo: Int,
    summaryIncorrectItem: IncorrectAnswer
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
                        questionNo, summaryIncorrectItem.question
                    ),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    modifier = Modifier
                        .size(35.dp),
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = incorrectAnswerColor
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(
                    id = R.string.answer_card_user_answer,
                    summaryIncorrectItem.usersAnswer
                )
            )
            Text(
                text = stringResource(
                    id = R.string.answer_card_correct_answer,
                    summaryIncorrectItem.correctAnswer
                )
            )
        }
    }
}

@Preview
@Composable
private fun LazyListSummaryIncorrectItemPreview(){
    LearningAppTheme{
        SummaryIncorrectItem(
            summaryIncorrectItem = IncorrectAnswer(
                "Question 1 with very big number of characters in it which creates so me problems to read",
                "User IncorrectAnswer",
                "Correct Answer"
            ),
            questionNo = 1
        )
    }
}