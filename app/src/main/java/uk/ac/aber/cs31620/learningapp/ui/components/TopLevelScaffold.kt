package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Creates the page scaffold to contain top app bar, navigation drawer,
 * bottom navigation button and of course the page content.
 * @param navController To pass through the NavHostController since navigation is required
 * @param pageContent So that callers can plug in their own page content.
 * By default an empty lambda.
 * @author Chris Loftus
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    pageTitle: String,
    coroutineScope: CoroutineScope,
    floatingActionButton: @Composable () -> Unit = {},
    snackBarHostState: SnackbarHostState? = null,
    snackBarContent: @Composable (SnackbarData) -> Unit = {},
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {},
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    MainPageNavigationDrawer(
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                MainPageTopAppBar(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
                    },
                    appBarTitle = pageTitle
                )
            },
            floatingActionButton = floatingActionButton,
            snackbarHost = {
                snackBarHostState?.let {
                    SnackbarHost(hostState = snackBarHostState) { data ->
                        snackBarContent(data)
                    }
                }
            },
            bottomBar = {
                AppBottomNavBar(navController)
            },
            content = { innerPadding ->
                pageContent(innerPadding)
            }
        )
    }
}