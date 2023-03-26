package uk.ac.aber.cs31620.learningapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.store.AppLanguagesStore
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.templates.AllScreensTemplate
import uk.ac.aber.cs31620.learningapp.ui.theme.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    firstAppStart: Boolean = false
){
    val chooseLanguageDialogOpen =
        rememberSaveable{ mutableStateOf(firstAppStart)}

    AllScreensTemplate(
        navController = navController,
        templateTitle = stringResource(id = R.string.home_title),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = NavRoutes.AddNewWord.route)
                },
                containerColor = tertiaryColor,
                contentColor = secondaryColor,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(65.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription =
                    stringResource(R.string.add_translation_img)
                )
            }
        }
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_content_title),
                fontSize = 26.sp, color = primaryColor,
                modifier = Modifier.padding(top = 25.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.learning_normal),
                contentDescription = stringResource(id = R.string.learning_image),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.padding(horizontal = 25.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(id = R.string.learning_desc_1),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.learning_desc_2),
                fontSize = 14.sp, color = primaryColor, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }

        FirstAppStartDialog(
            isOpen = chooseLanguageDialogOpen.value,
            changeDialogOpen = {
                chooseLanguageDialogOpen.value = it
            }
        )
    }
}
@Composable
private fun FirstAppStartDialog(
    isOpen: Boolean = false,
    changeDialogOpen:(Boolean) -> Unit = {}
){
    val context = LocalContext.current.applicationContext
    val appLanguagesStore = AppLanguagesStore(context)

    val dialogCoroutineScope = rememberCoroutineScope()

    val dialogMainLanguage = rememberSaveable{ mutableStateOf("") }
    val dialogLearningLanguage = rememberSaveable{ mutableStateOf("") }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = stringResource(id = R.string.dialog_choose_languages_title))
            },
            text = {
                Column {
                    Text(text = stringResource(id = R.string.dialog_choose_languages_desc),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )

                    Divider(thickness = 1.dp,
                        color = diverColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp
                            )
                    )


                    OutlinedTextField(
                        value = dialogMainLanguage.value,
                        onValueChange = {
                            dialogMainLanguage.value = it
                        },
                        modifier = Modifier.fillMaxWidth(0.95f),
                        label = {
                            Text(text= stringResource(id = R.string.dialog_choose_languages_input_1_language))
                        }
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        value = dialogLearningLanguage.value,
                        onValueChange = {
                            dialogLearningLanguage.value = it
                        },
                        modifier = Modifier.fillMaxWidth(0.95f),
                        label = {
                            Text(text= stringResource(id = R.string.dialog_choose_languages_input_2_language))
                        }
                    )
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        dialogCoroutineScope.launch {
                            appLanguagesStore.setAppLanguages(
                                dialogMainLanguage.value,
                                dialogLearningLanguage.value
                            )
                            changeDialogOpen(false)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ){
                    Text(stringResource(id = R.string.save))
                }
            }
        )
    }
}

@Preview
@Composable
private fun HomeContentPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        HomeScreen(navController)
    }
}