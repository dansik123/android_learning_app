package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.components.AnagramBlankLetters
import uk.ac.aber.cs31620.learningapp.ui.components.AnagramWordSelectLetters
import uk.ac.aber.cs31620.learningapp.ui.components.TranslationTextComponent
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.theme.*
import uk.ac.aber.cs31620.learningapp.utils.AnagramEnabledPair
import uk.ac.aber.cs31620.learningapp.utils.customMutableStateListOf
import java.util.*

@Composable
fun AnagramGameScreen(
    navController: NavHostController,
    appLanguage: Languages,
    translationsViewModel: TranslationViewModel = viewModel()
) {

    val randomTranslation by translationsViewModel.singleRandomTranslation.observeAsState()
    var blankCharsListState: SnapshotStateList<Char> = SnapshotStateList()
    var enabledCharsListState: SnapshotStateList<AnagramEnabledPair> = SnapshotStateList()

    val translationState: MutableState<Translation> = remember { mutableStateOf(Translation("","")) }
    val dialogState: MutableState<Boolean> = remember { mutableStateOf(false)}
    //Keeps track of every disabled character from enabledCharsListState
    // by storing it's index
    val disabledCharsIndexesListState: SnapshotStateList<Int> =
        remember { customMutableStateListOf(mutableListOf()) }
    val coroutineScope = rememberCoroutineScope()

    randomTranslation?.let{
        translation ->
        translationState.value = translation
        //initialize anagram chars for the game by saving it in pair.
        //each pair in the list holds character with boolean value for enabling/disabling
        // button with character after it has been clicked
        enabledCharsListState = remember{
            customMutableStateListOf(
                translation.to.
                    uppercase().
                    toList().
                    map{ translationChar -> AnagramEnabledPair(true, translationChar) }.
                    shuffled()
            )
        }
        //initialize list for blank letters which they are getting filled after user clicks on
        //letter button in anagram game
        blankCharsListState = remember {
            customMutableStateListOf(
                enabledCharsListState.map { ' ' }
            )
        }
    }

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.anagram_title),
    ){
        Column(modifier = Modifier.fillMaxWidth(0.80f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ){
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.anagram_game_title),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TranslationTextComponent(
                        translationLanguage = appLanguage.original,
                        translationWord = translationState.value.from,
                        isOriginalLanguage = true
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TranslationTextComponent(
                        translationLanguage = appLanguage.translated,
                        translationWord = null,
                        isOriginalLanguage = false
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Column(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AnagramBlankLetters(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        enabledCharsIndexesListState = disabledCharsIndexesListState,
                        blankCharsListState = blankCharsListState,
                        enabledCharsListState = enabledCharsListState
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    AnagramWordSelectLetters(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        enabledCharsListState = enabledCharsListState,
                        elementOnClick = {
                            index, item ->
                            //make the clicked button disabled
                            enabledCharsListState[index].enabledReverse()
                            //add index of clicked character button to list
                            //required to make reverse process in AnagramBlankLetters component
                            disabledCharsIndexesListState.add(index)
                            //fill character of this button at the end of blank list
                            blankCharsListState[
                                    disabledCharsIndexesListState.lastIndex] = item
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(0.95f),
                horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = {
                        dialogState.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    ),
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Text(stringResource(id = R.string.check))
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            navController.navigate(route = NavRoutes.Anagram.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(stringResource(id = R.string.next))
                }
            }
        }
        AnagramCheckDialog(
            blankAnagramInput = blankCharsListState.joinToString(separator = ""),
            anagramTranslation = translationState.value,
            isOpen = dialogState.value
        ) {
            dialogStateBool ->
            dialogState.value = dialogStateBool
        }
    }
}

@Composable
private fun AnagramCheckDialog(
    blankAnagramInput: String,
    anagramTranslation: Translation,
    isOpen:Boolean = false,
    isDialogOpenState: (Boolean) -> Unit
){
    val isCorrectAnagram = blankAnagramInput == anagramTranslation.to.uppercase()
    val displayCorrectTranslationState = remember{ mutableStateOf(false) }
    val anagramCorrectWordHidden = anagramTranslation.to.map { '?' }.joinToString(separator = "")

    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpenState(false)
            },
            icon = {
                if(isCorrectAnagram){
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = stringResource(R.string.correct),
                        tint = Color.Black
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = stringResource(R.string.wrong),
                        tint = Color.Black
                    )
                }
            },
            title = {
                Text(text =
                    stringResource(id =
                        if(isCorrectAnagram) R.string.correct
                        else R.string.wrong
                    )
                )
            },
            text = {
                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = stringResource(
                            id = R.string.your_answer,
                            blankAnagramInput.replace(' ', '_')
                        ),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.correct_answer,
                                if(displayCorrectTranslationState.value)
                                    anagramTranslation.to.uppercase(Locale.ROOT)
                                else anagramCorrectWordHidden
                        ),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        displayCorrectTranslationState.value =
                            !displayCorrectTranslationState.value
                    },
                    enabled = !isCorrectAnagram,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(
                        stringResource(id =
                            R.string.show_correct)
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        displayCorrectTranslationState.value = false
                        isDialogOpenState(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(
                        stringResource(id =
                            R.string.close)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun AnagramGameScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        AnagramGameScreen(
            navController,
            Languages("English", "Polish")
        )
    }
}