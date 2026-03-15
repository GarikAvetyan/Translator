package app.translator.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.translator.domain.repository.LanguagePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "language_preferences"
private const val DEFAULT_SOURCE = "en"
private const val DEFAULT_TARGET = "es"

private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class LanguagePreferencesRepositoryImpl(
    private val context: Context
) : LanguagePreferencesRepository {

    private object Keys {
        val sourceLanguage = stringPreferencesKey("source_language")
        val targetLanguage = stringPreferencesKey("target_language")
    }

    override val sourceLanguageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.sourceLanguage] ?: DEFAULT_SOURCE
        }

    override val targetLanguageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.targetLanguage] ?: DEFAULT_TARGET
        }

    override suspend fun setSourceLanguageCode(code: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.sourceLanguage] = code
        }
    }

    override suspend fun setTargetLanguageCode(code: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.targetLanguage] = code
        }
    }
}
