package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.model.Languages
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.textLabelColor

@Composable
fun SingleTranslationScreen(
    navController: NavHostController,
    translationId: Long,
    fromLanguages: Languages,
    translationsViewModel: TranslationViewModel = viewModel()
) {
    LaunchedEffect(key1 = translationId){
        translationsViewModel.findTranslationById(translationId)
    }

    val singleTranslation by
        translationsViewModel.singleTranslationSearch.observeAsState(
            Translation("","")
        )

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.one_translation_title),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(
                    id = R.string.one_translation_original_text_label, fromLanguages.original
                ),
                fontSize = 13.sp,
                color = textLabelColor
            )

            Text(
                text = singleTranslation.from,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(
                    id = R.string.one_translation_translated_text_label, fromLanguages.translated
                ),
                fontSize = 13.sp,
                color = textLabelColor
            )

            Text(
                text = singleTranslation.to,
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            route = NavRoutes.EditTranslation.route +"/${ singleTranslation.id }"
                        )
                    },
                    containerColor = tertiaryColor,
                    contentColor = secondaryColor,
                    modifier = Modifier
                        .size(55.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription =
                        stringResource(R.string.edit_title)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun SingleTranslationScreenPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        SingleTranslationScreen(
            navController,
            translationId = 1,
            Languages("English", "Polish")
        )
    }
}