package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.nonPrimaryTextColor
import uk.ac.aber.cs31620.learningapp.ui.theme.primaryColor
import uk.ac.aber.cs31620.learningapp.ui.theme.secondaryColor

@Composable
fun RowScope.CustomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    icon: @Composable () -> Unit,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = secondaryColor,
        selectedTextColor = secondaryColor,
        unselectedIconColor = nonPrimaryTextColor,
        unselectedTextColor = nonPrimaryTextColor,
        indicatorColor = primaryColor
    )
){
    val styledIcon = @Composable {
        val iconColor by colors.iconColor(selected = selected)
        CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
    }

    val styledLabel: @Composable (() -> Unit)? = label?.let {
        @Composable {
            val style = MaterialTheme.typography.labelMedium
            val textColor by colors.textColor(selected = selected)
            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(style, content = label)
            }
        }
    }
    val indicator = @Composable {
        Box(
            Modifier.background(
                color = colors.indicatorColor,
            ).fillMaxSize()
        )
    }

    Box(
        modifier = Modifier
            .padding(5.dp, 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .weight(1f)
            .fillMaxHeight(),
    ){
        if(selected) indicator()

        Column(
            modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            styledIcon()
            if(styledLabel!=null) {
                styledLabel()
            }
        }
    }
}

@Preview
@Composable
fun CustomNavigationBarItemPreview(){
    LearningAppTheme{
        NavigationBar(
            modifier = Modifier.clip(RoundedCornerShape(10.dp,10.dp,0.dp, 0.dp))
        ) {
            CustomNavigationBarItem(
                label = { Text(text ="Home") },
                selected = true,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Home",
                    )
                }
            )
            CustomNavigationBarItem(
                label = { Text(text ="Home") },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Home",
                    )
                }
            )
            CustomNavigationBarItem(
                label = { Text(text ="Home") },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Home",
                    )
                }
            )
            CustomNavigationBarItem(
                label = { Text(text ="Home") },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Home",
                    )
                }
            )
        }
    }
}