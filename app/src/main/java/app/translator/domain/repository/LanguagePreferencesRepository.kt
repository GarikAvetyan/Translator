package app.translator.domain.repository

import kotlinx.coroutines.flow.Flow

interface LanguagePreferencesRepository {
    val sourceLanguageCode: Flow<String>
    val targetLanguageCode: Flow<String>
    suspend fun setSourceLanguageCode(code: String)
    suspend fun setTargetLanguageCode(code: String)
}
