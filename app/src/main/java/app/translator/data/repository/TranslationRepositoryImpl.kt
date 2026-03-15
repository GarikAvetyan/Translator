package app.translator.data.repository

import app.translator.data.remote.LectoTranslateRemoteDataSource
import app.translator.domain.model.TranslationResult
import app.translator.domain.repository.TranslationRepository

class TranslationRepositoryImpl(
    private val remoteDataSource: LectoTranslateRemoteDataSource
) : TranslationRepository {
    override suspend fun translate(text: String, source: String, target: String): TranslationResult {
        val response = remoteDataSource.translate(text, source, target)
        val translated = response.translations.firstOrNull()
            ?.translated
            ?.firstOrNull()
            .orEmpty()
        return TranslationResult(translatedText = translated)
    }
}
