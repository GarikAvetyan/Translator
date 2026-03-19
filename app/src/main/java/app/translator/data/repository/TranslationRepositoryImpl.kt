package app.translator.data.repository

import app.translator.data.remote.LectoTranslateRemoteDataSource
import app.translator.domain.model.TranslationResult
import app.translator.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val remoteDataSource: LectoTranslateRemoteDataSource
) : TranslationRepository {

    override suspend fun translate(
        text: String,
        source: String,
        target: String
    ): TranslationResult {
        return try {
            val response = remoteDataSource.translate(text, source, target)
            val translated = response.translations.firstOrNull()
                ?.translated
                ?.firstOrNull()

            if (translated.isNullOrBlank()) {
                TranslationResult.Error(IllegalStateException("Empty translation response"))
            } else {
                TranslationResult.Success(translatedText = translated)
            }
        } catch (e: Exception) {
            TranslationResult.Error(e)
        }
    }
}
