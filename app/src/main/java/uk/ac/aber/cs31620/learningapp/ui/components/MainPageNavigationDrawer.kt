package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.store.AppLanguagesStore
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageNavigationDrawer(
    drawerState: DrawerState,
    content: @Composable () -> Unit = {},
) {

    val items = listOf(
        Pair(
            Icons.Default.Settings,
            stringResource(R.string.language_change)
        ),
        Pair(
            Icons.Default.Feedback,
            stringResource(R.string.feedback)
        )
    )

    val dialogBooleanState = rememberSaveable { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val selectedItem = rememberSaveable { mutableStateOf(0) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .height(65.dp)
                        .fillMaxWidth(0.80f)
                ) {
                    Text(
                        text = "Learning app",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(thickness = 1.dp,
                    color = diverColor,
                    modifier = Modifier.fillMaxWidth(0.80f)
                )

                items.forEachIndexed { index, item ->
                    Spacer(modifier = Modifier.height(20.dp))
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.first,
                                contentDescription = item.second
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .requiredHeight(65.dp),
                        shape = RoundedCornerShape(10),
                        label = { Text(item.second) },
                        selected = index == selectedItem.value,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = secondaryColor,
                            selectedIconColor = secondaryColor,
                            selectedContainerColor = primaryColor,
                            unselectedIconColor = nonPrimaryTextColor,
                            unselectedContainerColor = secondaryColor
                        ),
                        onClick = {
                            selectedItem.value = index
                            if (index == 0) {
                                dialogBooleanState.value = true
                            }
                        }
                    )
                }
            }
            ChangeLanguageDialog(
                isOpen = dialogBooleanState.value,
                isDialogOpenState = {
                    dialogBooleanState.value = it
                }
            )
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangeLanguageDialog(
    isOpen: Boolean,
    isDialogOpenState: (Boolean) -> Unit = {},
    translationViewModel: TranslationViewModel = viewModel()
){
    val context = LocalContext.current.applicationContext
    val appLanguagesStore = AppLanguagesStore(context)
    val appLanguages: State<Languages> = appLanguagesStore.getAppLanguages.collectAsState(
        initial = Languages("undefined","undefined")
    )

    val coroutineScope = rememberCoroutineScope()
    var radioOptions: List<Pair<String,String>>

    appLanguages.let {
        radioOptions = listOf(
            Pair(stringResource(id = R.string.source_language), appLanguages.value.original),
            Pair(stringResource(id = R.string.translated_language), appLanguages.value.translated)
        )
    }

    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    val dialogNewLanguage = remember{ mutableStateOf("") }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpenState(false)
            },
            title = {
                Text(text = stringResource(id = R.string.language_change_dialog_title))
            },
            text = {
                Column {
                    Text(text = stringResource(id = R.string.change_language_desc),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    Text(text = stringResource(id = R.string.change_language_warning),
                        color = errorColor)

                    Divider(thickness = 1.dp,
                        color = diverColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp
                            )
                    )

                    radioOptions.forEach { radioPair ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (radioPair.first == selectedOption),
                                onClick = {
                                    onOptionSelected(radioPair.first)
                                    dialogNewLanguage.value = radioPair.second
                                }
                            )
                            Text(
                                text = radioPair.first,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = dialogNewLanguage.value,
                        onValueChange = {
                            dialogNewLanguage.value = it
                        },
                        modifier = Modifier.fillMaxWidth(0.95f),
                        label = {
                            Text(text= stringResource(id = R.string.new_language))
                        }
                    )
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        translationViewModel.deleteAllTranslations(
                            afterExecution = {
                                if(selectedOption == radioOptions[0].first){
                                    coroutineScope.launch {
                                        appLanguagesStore.setAppLanguages(
                                            dialogNewLanguage.value,
                                            radioOptions[1].second
                                        )
                                    }
                                }else{
                                    coroutineScope.launch {
                                        appLanguagesStore.setAppLanguages(
                                            radioOptions[0].second,
                                            dialogNewLanguage.value,
                                        )
                                    }
                                }
                                isDialogOpenState(false)
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ){
                    Text(text = stringResource(id = R.string.confirm))
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
                    Text(text = stringResource(id = R.string.dismiss))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MainPageNavigationDrawerPreview() {
    LearningAppTheme(dynamicColor = false) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        MainPageNavigationDrawer(drawerState)
    }
}

@Preview
@Composable
fun DialogPreviewScreen(){
    ChangeLanguageDialog(true)
}