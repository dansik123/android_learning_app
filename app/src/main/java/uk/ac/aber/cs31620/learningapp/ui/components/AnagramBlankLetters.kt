package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.utils.AnagramEnabledPair
import uk.ac.aber.cs31620.learningapp.utils.customMutableStateListOf

@Composable
fun AnagramBlankLetters(
    modifier: Modifier = Modifier,
    enabledCharsIndexesListState: MutableList<Int>,
    blankCharsListState: SnapshotStateList<Char>,
    enabledCharsListState: SnapshotStateList<AnagramEnabledPair>
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        items(items = blankCharsListState){
            item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .padding(5.dp, 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(25.dp)
                        .background(secondaryColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = item.toString())
                }
                Divider(thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier.width(20.dp)
                )
            }
        }
        item {
            IconButton(
                enabled = enabledCharsIndexesListState.isNotEmpty(),
                onClick = {
                    //make last letter blank
                    blankCharsListState[enabledCharsIndexesListState.lastIndex] = ' '
                    //get index of the last disabled button and delete it from the list
                    val lastIndex: Int = enabledCharsIndexesListState.removeLast()
                    //make it enabled
                    enabledCharsListState[lastIndex].enabledReverse()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnagramBlankLettersPreview(){
    val enabledCharsIndexesListState: SnapshotStateList<Int> = remember {
        customMutableStateListOf(
            mutableListOf()
        )
    }

    val blankCharsListState: SnapshotStateList<Char> = remember {
        customMutableStateListOf(
            mutableListOf(
                ' ',' ',' ',' '
            )
        )
    }

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
        AnagramBlankLetters(
            enabledCharsIndexesListState = enabledCharsIndexesListState,
            blankCharsListState = blankCharsListState,
            enabledCharsListState = enabledCharsListState
        )
    }
}