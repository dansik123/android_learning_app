package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.ui.theme.primaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor

@Composable
fun MainPageTopAppBar(
    onClick: () -> Unit = {},
    appBarTitle: String
){
    CenterAlignedTopAppBar(
        title = {
            Text(appBarTitle)
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription =
                        stringResource(R.string.nav_drawer_menu)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = primaryColor,
            titleContentColor = secondaryColor,
            navigationIconContentColor = secondaryColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearningAppTheme {
        MainPageTopAppBar(
            appBarTitle = "Home"
        )
    }
}
