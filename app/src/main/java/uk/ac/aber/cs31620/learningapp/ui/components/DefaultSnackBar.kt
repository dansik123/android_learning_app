package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultSnackBar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        modifier = modifier,
        content = {
            Text(text = data.visuals.message)
        },
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                Text(text = actionLabel)
            }
        }
    )
}