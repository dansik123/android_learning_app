package uk.ac.aber.cs31620.learningapp.utils

data class AnagramEnabledPair(
    var enabled: Boolean,
    var character: Char
){
    fun enabledReverse(){
        this.enabled = !this.enabled
    }
}
