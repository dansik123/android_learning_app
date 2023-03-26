package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.utils.AnagramEnabledPair
import uk.ac.aber.cs31620.learningapp.utils.customMutableStateListOf

@Composable
fun AnagramWordSelectLetters(
    modifier: Modifier = Modifier,
    enabledCharsListState: List<AnagramEnabledPair>,
    elementOnClick: (index: Int, item: Char) -> Unit
        = { _,_ -> }
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        itemsIndexed(
            items = enabledCharsListState
        ) {
            index, item ->
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(25.dp)
                        .background(secondaryColor)
                        .alpha((if (item.enabled) 1.0f else 0.5f))
                        .clickable(
                            enabled = item.enabled,
                            onClick = { elementOnClick(index, item.character) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.character.toString())
                }
            }
        }
    }
}

@Preview
@Composable
private fun AnagramWordSelectLettersPreview(){
    val enabledCharsListState: SnapshotStateList<AnagramEnabledPair> = remember {
        customMutableStateListOf(
            mutableListOf(
                AnagramEnabledPair(true, 'C'),
                AnagramEnabledPair(true,'B'),
                AnagramEnabledPair(true,'A'),
                AnagramEnabledPair(true,'D')
            )
        )
    }
    LearningAppTheme{
        AnagramWordSelectLetters(
            enabledCharsListState = enabledCharsListState
        )
    }
}