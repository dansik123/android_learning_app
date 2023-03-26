package uk.ac.aber.cs31620.learningapp.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.Anagram
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.CheckTranslation
import uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack.Quiz
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Anagram, CheckTranslation, Quiz)
    return __AllIcons!!
  }
