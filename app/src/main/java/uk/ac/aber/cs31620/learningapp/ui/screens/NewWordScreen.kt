package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.components.DefaultSnackBar
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor

@Composable
fun NewWordScreen(
    navController: NavHostController,
    appLanguages: Languages,
    translationsViewModel: TranslationViewModel = viewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val mainLanguageInput = rememberSaveable { mutableStateOf("") }
    val learningLanguageInput = rememberSaveable { mutableStateOf("") }
    val snackBarMessage: String = stringResource(R.string.added_translation_message)
    val coroutineScope = rememberCoroutineScope()

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.add_word_title),
        snackBarContent = { data ->
            DefaultSnackBar(
                data = data,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        },
        snackBarHostState = snackBarHostState
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth(0.85f),
            ) {
                val (inputMainLanguage, inputLearningLanguage, submitBtn) = createRefs()

                OutlinedTextField(value = mainLanguageInput.value,
                    onValueChange = {
                        mainLanguageInput.value = it
                    },
                    modifier = Modifier
                        .constrainAs(inputMainLanguage) {
                            top.linkTo(parent.top, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth(),
                    label = {
                        Text(text = stringResource(id = R.string.insert_word_label, appLanguages.original))
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                mainLanguageInput.value = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                modifier = Modifier.size(20.dp),
                                contentDescription =
                                stringResource(id = R.string.cancel),
                            )
                        }
                    }
                )

                OutlinedTextField(value = learningLanguageInput.value,
                    onValueChange = {
                        learningLanguageInput.value = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.insert_word_label, appLanguages.translated))
                    },
                    modifier = Modifier
                        .constrainAs(inputLearningLanguage) {
                            top.linkTo(inputMainLanguage.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                learningLanguageInput.value = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                modifier = Modifier.size(20.dp),
                                contentDescription =
                                    stringResource(id = R.string.cancel),
                            )
                        }
                    }
                )

                Button(
                    onClick = {
                        translationsViewModel.addNewTranslation(
                            data = Translation(
                                mainLanguageInput.value,
                                learningLanguageInput.value
                            ),
                            afterExecution = {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = snackBarMessage
                                    )
                                }
                                mainLanguageInput.value = ""
                                learningLanguageInput.value = ""
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    ),
                    modifier = Modifier.constrainAs(submitBtn) {
                        top.linkTo(inputLearningLanguage.bottom, margin = 20.dp)
                        end.linkTo(parent.end)
                    }
                ) {
                    Text(stringResource(id = R.string.save))
                }
            }
        }
    }
}

@Preview
@Composable
private fun NewWordScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        NewWordScreen(
            navController = navController,
            appLanguages = Languages("English", "Polish")
        )
    }
}