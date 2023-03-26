package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.datasource.viewModels.TranslationViewModel
import uk.ac.aber.cs31620.learningapp.ui.icons.MyIconPack
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.Quiz
import uk.ac.aber.cs31620.learningapp.ui.navigation.GameItemGroup
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.disabledColorButton
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameItem(
    modifier: Modifier = Modifier,
    gameGroupData: GameItemGroup,
    cardColors: CardColors,
    navController: NavHostController,
    translationViewModel: TranslationViewModel = viewModel()
) {
    val dialogState = remember{ mutableStateOf(false) }
    val translationCountState = remember{ mutableStateOf(0) }
    val translationCount by translationViewModel.numberOfTranslations.observeAsState()
    translationCount?.let{
        translationCountState.value = it
    }

    Card(
        modifier = modifier
            .fillMaxSize(),
        colors = cardColors
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
        ) {
            val (gameMoreBtn, gameIcon, gameNameText, gameStartBtn) = createRefs()
            IconButton(
                onClick = {
                    dialogState.value = true
                },
                modifier = Modifier
                    .constrainAs(gameMoreBtn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(R.string.check),
                    modifier = Modifier.size(35.dp)
                )
            }

            Icon(
                imageVector = gameGroupData.gameIcon,
                contentDescription = gameGroupData.label,
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                    .constrainAs(gameIcon) {
                        top.linkTo(gameMoreBtn.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = gameGroupData.label,
                modifier = Modifier.constrainAs(gameNameText) {
                    top.linkTo(gameIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                fontSize = 20.sp
            )

            Button(
                enabled = translationCountState.value > gameGroupData.minGameCountStart,
                onClick = {
                    navController.navigate(route = gameGroupData.gameRoute)
                },
                modifier = Modifier.constrainAs(gameStartBtn) {
                    top.linkTo(gameNameText.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiaryColor,
                    contentColor = secondaryColor,
                    disabledContainerColor = disabledColorButton
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    fontSize = 15.sp
                )
            }
        }
    }

    GameDescriptionDialog(
        isOpen = dialogState.value,
        isDialogOpenState = {
            newBoolState -> dialogState.value = newBoolState
        },
        gameGroupData = gameGroupData
    )
}

@Composable
private fun GameDescriptionDialog(
    isOpen: Boolean,
    isDialogOpenState: (Boolean)->Unit = {},
    gameGroupData: GameItemGroup
){
    if (isOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpenState(false)
            },
            title = {
                Text(
                    text = gameGroupData.label
                )
            },
            text = {
                Text(text = gameGroupData.gameDesc)
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogOpenState(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ){
                    Text(stringResource(id = R.string.okay))
                }
            }
        )
    }
}

@Preview
@Composable
private fun GameDescriptionDialogPreview(){
    LearningAppTheme{
        GameDescriptionDialog(
            isOpen = true,
            gameGroupData = GameItemGroup(
                MyIconPack.Quiz,
                NavRoutes.Quiz.route,
                stringResource(id= R.string.quiz),
                stringResource(
                    id= R.string.quiz_game_desc,5
                ),
                5
            )
        )
    }
}

@Preview
@Composable
private fun GameItemPreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        GameItem(
            gameGroupData = GameItemGroup(
                MyIconPack.Quiz,
                NavRoutes.Quiz.route,
                "Quiz",
                "Simple game desc",
                5
            ),
            cardColors = CardDefaults.cardColors(),
            navController = navController
        )
    }
}

