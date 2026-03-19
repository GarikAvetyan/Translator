package app.translator.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.translator.domain.repository.LanguagePreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "language_preferences")

class LanguagePreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguagePreferencesRepository {

    override val sourceLanguageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.SOURCE_LANGUAGE] ?: DEFAULT_SOURCE
        }

    override val targetLanguageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.TARGET_LANGUAGE] ?: DEFAULT_TARGET
        }

    override suspend fun setSourceLanguageCode(code: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.SOURCE_LANGUAGE] = code
        }
    }

    override suspend fun setTargetLanguageCode(code: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.TARGET_LANGUAGE] = code
        }
    }

    private object Keys {
        val SOURCE_LANGUAGE = stringPreferencesKey("source_language")
        val TARGET_LANGUAGE = stringPreferencesKey("target_language")
    }

    private companion object {
        const val DEFAULT_SOURCE = "en"
        const val DEFAULT_TARGET = "es"
    }
}
