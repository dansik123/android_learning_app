package uk.ac.aber.cs31620.learningapp.utils

import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation

fun trimStringToSize(expectedLength: Int, stringToTrim: String): String{
    return if(stringToTrim.length > expectedLength)
        stringToTrim.take(expectedLength) + "..."
    else
        stringToTrim
}

fun searchTranslationFilter(data: Translation, searchPhrase: String): Boolean{
    return data.from.contains(searchPhrase, ignoreCase = true) ||
            data.to.contains(searchPhrase, ignoreCase = true)
}