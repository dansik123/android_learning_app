package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.model.QuizAnswersSingleton
import uk.ac.aber.cs31620.learningapp.ui.icons.materialDesignIcons
import uk.ac.aber.cs31620.learningapp.ui.icons.materialdesignicons.googleController
import uk.ac.aber.cs31620.learningapp.ui.navigation.NavRoutes
import uk.ac.aber.cs31620.learningapp.ui.navigation.navBottomBarRoutesList
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.navigation.IconGroup
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.tertiaryColor
import uk.ac.aber.cs31620.learningapp.utils.isInGameScreen

@Composable
fun AppBottomNavBar(navController: NavController) {
    val icons = mapOf(
        NavRoutes.Home to IconGroup(
            filledIcon = Icons.Filled.Home,
            label = stringResource(id = R.string.home)
        ),
        NavRoutes.Dictionary to IconGroup(
            filledIcon = Icons.Filled.ImportContacts,
            label = stringResource(id = R.string.dictionary)
        ),
        NavRoutes.Games to IconGroup(
            filledIcon = materialDesignIcons.googleController,
            label = stringResource(id = R.string.games)
        )
    )
    val dialogOpenState = remember { mutableStateOf(false) }
    val dialogChosenRoute = remember { mutableStateOf("") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(10.dp,10.dp,0.dp, 0.dp)),
        containerColor = secondaryColor
    ) {
        navBottomBarRoutesList.forEach { screen ->
            val isSelected = currentDestination
                ?.hierarchy?.any{ it.route == screen.route } == true
            val labelText = icons[screen]!!.label
            CustomNavigationBarItem(
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    if(isInGameScreen(navController)){
                        //open dialog and pass screen.route where should
                        // navigate to if user agreed to exit quiz
                        dialogChosenRoute.value = screen.route
                        dialogOpenState.value = true
                    }else{
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icons[screen]!!.filledIcon,
                        modifier = Modifier.size(35.dp),
                        contentDescription = labelText,
                    )
                }
            )
        }
    }

    ExitQuizDialog(
        isOpen = dialogOpenState.value,
        isDialogOpenState = {
            dialogOpenState.value = it
        },
        navController = navController,
        goToRoute = dialogChosenRoute.value
    )
}

//Dialog should show up only if user try to use navBar when it is in quiz
@Composable
private fun ExitQuizDialog(
    isOpen: Boolean = false,
    isDialogOpenState: (Boolean) -> Unit = {},
    navController: NavController,
    goToRoute: String
){
    if (isOpen) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = stringResource(id = R.string.quiz_title))
            },
            text = {
                Text(
                    text = stringResource(id = R.string.exit_quiz_dialog_text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogOpenState(false)
                        QuizAnswersSingleton.resetQuizAnswers()
                        navController.navigate(route = goToRoute)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tertiaryColor,
                        contentColor = secondaryColor
                    )
                ) {
                    Text(stringResource(id = R.string.yes))
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
                ) {
                    Text(stringResource(id = R.string.no))
                }
            }
        )
    }
}

@Preview
@Composable
private fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    LearningAppTheme(dynamicColor = false) {
        AppBottomNavBar(navController)
    }
}