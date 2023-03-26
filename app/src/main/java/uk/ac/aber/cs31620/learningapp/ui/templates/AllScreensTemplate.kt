package uk.ac.aber.cs31620.learningapp.ui.templates

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.ui.components.TopLevelScaffold
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme

@Composable
fun AllScreensTemplate(
    navController: NavHostController,
    templateTitle: String = "",
    floatingActionButton: @Composable () -> Unit = {},
    snackBarHostState: SnackbarHostState? = null,
    snackBarContent: @Composable (SnackbarData) -> Unit = {},
    content: @Composable () -> Unit ={}
) {
    val coroutineScope = rememberCoroutineScope()
    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        pageTitle = templateTitle,
        floatingActionButton = floatingActionButton,
        snackBarContent = snackBarContent,
        snackBarHostState = snackBarHostState
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun AllScreensTemplatePreview(){
    val navController = rememberNavController()
    LearningAppTheme{
        AllScreensTemplate(navController)
    }
}