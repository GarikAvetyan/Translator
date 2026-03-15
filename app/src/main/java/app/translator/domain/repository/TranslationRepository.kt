package app.translator.domain.repository

import app.translator.domain.model.TranslationResult

interface TranslationRepository {
    suspend fun translate(text: String, source: String, target: String): TranslationResult
}
