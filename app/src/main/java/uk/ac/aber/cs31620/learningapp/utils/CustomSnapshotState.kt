package uk.ac.aber.cs31620.learningapp.utils

import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> customMutableStateListOf(list: List<T>) =
    SnapshotStateList<T>().also { it.addAll(list) }