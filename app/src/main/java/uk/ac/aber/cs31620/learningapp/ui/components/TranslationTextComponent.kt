package uk.ac.aber.cs31620.learningapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import uk.ac.aber.cs31620.learningapp.R
import uk.ac.aber.cs31620.learningapp.ui.theme.LearningAppTheme
import uk.ac.aber.cs31620.learningapp.ui.theme.textLabelColor

@Composable
fun TranslationTextComponent(
    modifier: Modifier = Modifier,
    translationLanguage: String,
    translationWord: String?,
    isOriginalLanguage: Boolean = false,
){
    Column(modifier = modifier){
        Text(
            text = stringResource(
                id = (
                   if(isOriginalLanguage) (R.string.one_translation_original_text_label)
                    else R.string.one_translation_translated_text_label
                ), translationLanguage
            ),
            fontSize = 13.sp,
            color = textLabelColor
        )
        translationWord?.let{
            Text(
                text = it,
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
private fun TranslationTextComponentPreview(){
    LearningAppTheme{
        TranslationTextComponent(
            translationLanguage = "Polish",
            translationWord = "mąka",
            isOriginalLanguage = true
        )
    }
}

@Preview
@Composable
private fun TranslationTextComponentTwoPreview(){
    LearningAppTheme{
        TranslationTextComponent(
            translationLanguage = "English",
            translationWord = "flour",
            isOriginalLanguage = false
        )
    }
}