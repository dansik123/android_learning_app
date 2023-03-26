package uk.ac.aber.cs31620.learningapp.model

data class Languages(
    val original: String,
    val translated: String
){
    fun hasLanguages():Boolean = original.isNotEmpty() && translated.isNotEmpty()
}
