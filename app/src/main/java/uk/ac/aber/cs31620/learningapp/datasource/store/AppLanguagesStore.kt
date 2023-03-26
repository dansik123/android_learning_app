package uk.ac.aber.cs31620.learningapp.datasource.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.aber.cs31620.learningapp.model.Languages

class AppLanguagesStore(private val context: Context) {

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AppLanguages")
        val APP_MAIN_LANGUAGE = stringPreferencesKey("from_language")
        val APP_TRANSLATED_LANGUAGE = stringPreferencesKey("to_language")
    }

    val getAppLanguages: Flow<Languages> = context.dataStore.data.map {
        preferences ->
            Languages(
                preferences[APP_MAIN_LANGUAGE]?: "",
                preferences[APP_TRANSLATED_LANGUAGE]?: "")
    }

    suspend fun setAppLanguages(mainLanguage: String, secondaryLanguage: String){
        context.dataStore.edit {
            preferences ->
            preferences[APP_MAIN_LANGUAGE] = mainLanguage
            preferences[APP_TRANSLATED_LANGUAGE] = secondaryLanguage
        }
    }
}