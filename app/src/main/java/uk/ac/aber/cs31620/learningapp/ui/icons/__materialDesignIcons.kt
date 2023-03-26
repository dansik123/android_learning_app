package uk.ac.aber.cs31620.learningapp.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import uk.ac.aber.cs31620.learningapp.ui.icons.materialdesignicons.googleController
import kotlin.collections.List as ____KtList

public object materialDesignIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val materialDesignIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(googleController)
    return __AllIcons!!
  }
