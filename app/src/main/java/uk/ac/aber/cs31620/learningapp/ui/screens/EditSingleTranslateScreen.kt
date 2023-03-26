package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor

@Composable
fun EditSingleTranslationScreen(
    navController: NavHostController,
    translationId: Long,
    fromLanguages: Languages,
    translationsViewModel: TranslationViewModel = viewModel()
) {
    LaunchedEffect(key1 = translationId){
        translationsViewModel.findTranslationById(translationId)
    }

    val singleTranslation: Translation? by
        translationsViewModel.singleTranslationSearch.observeAsState()

    val mainLanguageInput = rememberSaveable { mutableStateOf("") }
    val learningLanguageInput = rememberSaveable { mutableStateOf("") }

    singleTranslation?.let { translation ->
        mainLanguageInput.value = translation.from
        learningLanguageInput.value = translation.to
    }

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.edit_title),
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth(0.85f),
            ) {
                val (inputMainLanguage, inputLearningLanguage, submitBtn) = createRefs()

                    OutlinedTextField(
                        value = mainLanguageInput.value,
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
                            Text(
                                text = stringResource(
                                    id = R.string.one_translation_original_text_label, fromLanguages.original
                                ),
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    mainLanguageInput.value = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
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
                            Text(
                                text = stringResource(R.string.one_translation_translated_text_label, fromLanguages.translated)
                            )
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
                                    imageVector = Icons.Outlined.Cancel,
                                    modifier = Modifier.size(20.dp),
                                    contentDescription =
                                    stringResource(id = R.string.cancel),
                                )
                            }
                        }
                    )

                    Button(
                        onClick = {
                            val updatedTranslation =
                                Translation(
                                    translationId,
                                    mainLanguageInput.value,
                                    learningLanguageInput.value
                                )
                            translationsViewModel.updateTranslationById(updatedTranslation)
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
                /*
                * TODO: if TranslationData is not present allow user go back to list
                *  Just in case
                * */

            }
        }
    }
}

@Preview
@Composable
private fun EditSingleTranslationScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        EditSingleTranslationScreen(
            navController,
            translationId = 1,
            Languages("English", "Polish")
        )
    }
}