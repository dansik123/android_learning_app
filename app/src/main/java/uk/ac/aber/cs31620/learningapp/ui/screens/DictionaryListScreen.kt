package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.ui.icons.MyIconPack
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.CheckTranslation
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.theme.*
import uk.ac.aber.cs31620.learningapp.utils.searchTranslationFilter
import uk.ac.aber.cs31620.learningapp.utils.trimStringToSize

@Composable
fun DictionaryListScreen(
    navController: NavHostController,
    translationsViewModel: TranslationViewModel = viewModel()
) {

    val translationsList by translationsViewModel.translationsList.observeAsState(listOf())
    val searchText = rememberSaveable{ mutableStateOf("") }
    val selectedDeleteTranslation = remember { mutableStateOf(Translation("","")) }
    val dialogState = rememberSaveable { mutableStateOf(false) }

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.dictionary_title),
    ){
        Column {
            TextField(
                value = searchText.value,
                onValueChange = { input ->
                    searchText.value = input
                },
                label = {
                    Text(text = stringResource(id = R.string.search_input_text))
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                state = rememberLazyListState()
            ) {
                items(items =
                    translationsList.filter {
                        searchTranslationFilter(it, searchText.value)
                    }
                ) { item -> LazyListItem(
                        itemTranslation = item,
                        checkTranslationOnClick = {
                            navController.navigate(
                                route = NavRoutes.SingleWordTranslation.route +"/${item.id}"
                            )
                        },
                        deleteDialogOnClick = {
                            translation ->
                            selectedDeleteTranslation.value = translation
                            dialogState.value = true
                        }
                    )
                }
            }
            DeleteTranslationDialog(
                isOpen = dialogState.value,
                isDialogOpenState = {
                    dialogState.value = it
                },
                selectedTranslation = selectedDeleteTranslation.value,
                translationsViewModel = translationsViewModel
            )
        }
    }
}

@Composable
private fun DeleteTranslationDialog(
    isOpen: Boolean,
    isDialogOpenState: (Boolean)->Unit = {},
    selectedTranslation: Translation = Translation("", ""),
    translationsViewModel: TranslationViewModel
){
    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpenState(false)
            },
            title = {
                Text(
                    text = stringResource(id = R.string.delete_tile_dialog)
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = nonPrimaryTextColor
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(
                            id =
                                R.string.delete_dialog_translation_text,
                                selectedTranslation.from,
                                selectedTranslation.to
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        translationsViewModel.deleteTranslationById(
                            id=selectedTranslation.id,
                            afterExecution = {
                                isDialogOpenState(false)
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ){
                    Text(stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogOpenState(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ){
                    Text(stringResource(id = R.string.dismiss))
                }
            }
        )
    }
}

@Composable
private fun LazyListItem(
    itemTranslation: Translation,
    checkTranslationOnClick: () -> Unit = {},
    deleteDialogOnClick: (dictionaryItem: Translation) -> Unit = {}
){
    val maxListWordLength = 45

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(all = 10.dp)
        ){
            Text(trimStringToSize(maxListWordLength, itemTranslation.from))
            Spacer(modifier = Modifier.height(10.dp))
            Text(trimStringToSize(maxListWordLength, itemTranslation.to))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            IconButton(
                onClick = {
                    checkTranslationOnClick()
                },
                modifier =
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                ,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = secondaryColor
                )

            ) {
                Icon(
                    imageVector = MyIconPack.CheckTranslation,
                    contentDescription =
                        stringResource(R.string.add),
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = {
                    deleteDialogOnClick(itemTranslation)
                },
                modifier =
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                ,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = secondaryColor
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription =
                        stringResource(R.string.delete),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DictionaryListContentPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        DictionaryListScreen(
            navController
        )
    }
}

@Preview
@Composable
private fun LazyListItemPreview(){
    LearningAppTheme{
        LazyListItem(itemTranslation =  Translation(2, "food", "jedzenie"))
    }
}