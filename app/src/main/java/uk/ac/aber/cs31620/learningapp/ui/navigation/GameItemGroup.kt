package uk.ac.aber.cs31620.learningapp.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class GameItemGroup(
    val gameIcon: ImageVector,
    val gameRoute: String,
    val label: String,
    val gameDesc: String,
    val minGameCountStart: Int
)